package org.example.dao.hibernate;


import org.example.configuration.HibernateUtil;
import org.example.dao.IVehicleRepository;
import org.example.model.User;
import org.example.model.Vehicle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;


public class VehicleDAO implements IVehicleRepository {

    private static VehicleDAO vehicleDAO ;
    private final SessionFactory sessionFactory;

    private VehicleDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public static VehicleDAO getInstance(){
        if(vehicleDAO == null){
            vehicleDAO  = new VehicleDAO();
        }
        return vehicleDAO;
    }

    @Override
    public boolean rentVehicle(String plate, String login) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean success = false;
        try {
            transaction = session.beginTransaction();

            User user = session.get(User.class, login);
            Vehicle vehicle = session.get(Vehicle.class, plate);

            if (user != null && vehicle != null && user.getVehicle() == null) {
                vehicle.setUser(user);
                vehicle.setRent(true);
                user.setVehicle(vehicle);

                session.saveOrUpdate(user);
                session.saveOrUpdate(vehicle);
                success = true;
                transaction.commit();
            } else {
                if (transaction != null) {
                    success = false;
                    transaction.rollback();
                }
                return false;
            }
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return success;
    }


    @Override
    public boolean addVehicle(Vehicle vehicle) {
        Session session = sessionFactory.openSession();
        boolean success=false;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(vehicle);
            transaction.commit();
            success = true;
        } catch (RuntimeException e) {
            if (transaction != null) {
                success = false;
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return success;
    }
    @Override
    public boolean removeVehicle(String plate) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean success = false;
        try {
            transaction = session.beginTransaction();
            Vehicle vehicle = session.get(Vehicle.class, plate);
            if (vehicle != null ) {
                session.remove(vehicle);
                transaction.commit();
            } else {
                return success;
            }
            success = true;
        } catch (RuntimeException e) {
            if (transaction != null){
                success = false;
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return  success;
    }

    @Override
    public Vehicle getVehicle(String plate) {
        Session session = sessionFactory.openSession();
        try {
            Vehicle vehicle = session.get(Vehicle.class, plate);
            return vehicle;
        } finally {
            session.close();
        }
    }

    //Must implement old interface. Plate is no longer needed when User has Vehicle.
    public boolean returnVehicle(String plate,String login) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        boolean success = false;
        try {
            transaction = session.beginTransaction();
            Vehicle vehicle = session.get(Vehicle.class, plate);
            vehicle.setRent(false);
            User user = session.get(User.class,login);
            success = true;
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                success = false;
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }
        return success;
    }

    @Override
    public Collection<Vehicle> getVehicles() {
        Collection<Vehicle>  vehicles;
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            vehicles = session.createQuery("FROM Vehicle ", Vehicle.class).getResultList();
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return vehicles;

}
}
