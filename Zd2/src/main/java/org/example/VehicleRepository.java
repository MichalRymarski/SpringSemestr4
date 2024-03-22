package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VehicleRepository implements VehicleRepo {
    private List<Vehicle> vehicleList;
    private int counter;
    private static VehicleRepository instance = null;
    private UserRepository userRepository;
    private User user;

    private VehicleRepository() {
        this.user = User.getInstance();
        this.vehicleList = new ArrayList<>();
        this.counter = 1;
    }

    public static VehicleRepository getInstance() {
        if (instance == null) {
            instance = new VehicleRepository();
        }
        return instance;
    }

    private UserRepository getUserRepository() {
        if (userRepository == null) {
            userRepository = UserRepository.getInstance();
        }
        return userRepository;
    }

    @Override
    public void rentCar(final Integer id) {
        if (Authentication.getInstance().loggedIn) {
            UserRepository userRepository = getUserRepository();
            Vehicle vehicle = vehicleList.stream()
                .filter(vec -> vec.getID().equals(id))
                .findFirst()
                .orElse(null);
            if (vehicle != null) {
                userRepository.changeCurrentCar(id, vehicle);
                return;
            }
        }
        System.out.println("Zla komenda lub brak uprawnien");
        return;
    }


    @Override
    public void returnCar(final Integer id) {
        if (Authentication.getInstance().loggedIn) {
            Vehicle vehicle = vehicleList.stream()
                .filter(vec -> vec.getID().equals(id))
                .findFirst()
                .orElse(null);
            if (vehicle != null) {
                if (vehicle.rented == false) {
                    System.out.println("ALREADY RETURNED");
                    return;
                }
                user.setVehicleID(0);
                vehicle.rented = false;
                return;
            }
        }
        System.out.println("Zla komenda lub brak uprawnien");
        return;
    }

    @Override
    public List<Vehicle> getVehicles() {
        return vehicleList;
    }

    @Override
    public void save(Vehicle vehicle) {
        vehicle.setID(counter);
        counter++;
        vehicleList.add(vehicle);
    }

    @Override
    public void addVehicle(final Vehicle vehicle) {
        updateUser();
        String role = user.getRole();
        if (role.equals("ADMIN")) {
            vehicleList.add(vehicle);
            System.out.println("dodano pojazd");
            return;
        }

        System.out.println("NIEWYSTARCZAJACE UPRAWNIENIA");
        return;
    }
    private void updateUser() {
        this.user = User.getInstance();
    }
    @Override
    public void removeVehicle(final int ID) {
        updateUser();
        String role = user.getRole();

        if (role.equals("ADMIN")) {
            if (doesVehicleExist(ID)) {
                System.out.println("Usunieto pojazd");
                return;
            }

            System.out.println("NIEWYSTARCZAJACE UPRAWNIENIA");
            return;
        }
    }

    public boolean doesVehicleExist(final int ID) {
        Iterator<Vehicle> iterator = vehicleList.iterator();
        while (iterator.hasNext()) {
            Vehicle vehicle = iterator.next();
            if (vehicle.getID() == ID) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}
