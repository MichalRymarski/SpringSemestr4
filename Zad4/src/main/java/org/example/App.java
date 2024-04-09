package org.example;

import org.apache.commons.codec.digest.DigestUtils;
import org.example.authenticate.Authenticator;
import org.example.configuration.HibernateUtil;
import org.example.dao.IUserRepository;
import org.example.dao.IVehicleRepository;
import org.example.dao.hibernate.UserDAO;
import org.example.dao.hibernate.VehicleDAO;
import org.example.model.Car;
import org.example.model.Motorcycle;
import org.example.model.User;
import org.example.model.Vehicle;

import java.util.Scanner;

public class App {
    public static  User user = null;
    private final Scanner scanner = new Scanner(System.in);
    private final IUserRepository iur = UserDAO.getInstance();

    private final IVehicleRepository ivr = VehicleDAO.getInstance();

    public void run() {

        System.out.println("LOG IN");

        user = Authenticator.login(scanner.nextLine(),scanner.nextLine());
        if(user!=null){
            System.out.println("logged in!!");

        String response = "";
        boolean running=true;
        while(running) {

            System.out.println("-----------MENU------------");
            System.out.println("00 - show info");
            System.out.println("01 - show cars");
            System.out.println("02 - show users");
            System.out.println("1 - rent car");
            System.out.println("2 - return car");
            System.out.println("3 - show car info");
            System.out.println("6 - add car");
            System.out.println("7 - remove car");
            System.out.println("8 - add user");
            System.out.println("9 - remove user");
            response = scanner.nextLine();
            switch (response) {
                case "00":
                        user = iur.getUser(user.getLogin());
                        System.out.println(user);
                    break;
                case "01":
                    for (Vehicle v : ivr.getVehicles()) {
                        System.out.println(v);
                    }
                    break;
                case "02":
                    for (User u: iur.getUsers()) {
                        System.out.println(u);
                    }
                    break;
                case "1":
                    System.out.println("Rent car by plates:");
                    String plate = scanner.nextLine();
                    ivr.rentVehicle(plate,user.getLogin());
                    user = iur.getUser(user.getLogin());
                    break;
                case "2":
                    System.out.println("function for return car");

                    String plateForReturn = user.getVehicle().getPlate();
                    ivr.returnVehicle(plateForReturn,user.getLogin());
                    user = iur.getUser(user.getLogin());
                    break;
                case "3":
                    System.out.println("plates:");
                    String plateToShow = scanner.nextLine();
                    System.out.println(ivr.getVehicle(plateToShow));

                    break;
                case "6":
                    addVehicle();
                    break;

                case "7":
                    removeCarByPlate();
                    break;
                case "8":
                    addUser();
                    break;
                case "9":
                    removeUser();
                    break;

                default:
                    running=false;
                }
            }
        }else{
            System.out.println("Bledne dane!");
        }
        System.exit(0);
    }

    private void addVehicle() {
        System.out.println("add car (only) separator is ; String brand, String model, int year, double price, String plate ");
        ////Motorcycle(String brand, String model, int year, double price, String plate, String category)
        String line = scanner.nextLine();
        String[] arr = line.split(";");
        System.out.println("what do you want to add? Car/Motorcycle");
        line = scanner.nextLine();
        if (line.equals("Car")) {
            addCar(arr);
        }else if(line.equals("Motorcycle")){
            addMotorcycle(arr);
        }
    }

    private void addMotorcycle(final String[] arr) {
        ivr.addVehicle(new Motorcycle(arr[0],
            arr[1],
            Integer.parseInt(arr[2]),
            Double.parseDouble(arr[3]),
            arr[4],
            arr[5]));
    }

    private void addCar(final String[] arr) {
        ivr.addVehicle(new Car(arr[0],
                        arr[1],
                        Integer.parseInt(arr[2]),
                        Double.parseDouble(arr[3]),
                        arr[4]));
    }

    private void addUser() {
        String  login = scanner.nextLine();
        String  password = scanner.nextLine();
        password = DigestUtils.sha256Hex(password);
        User user = new User(login,password);
        iur.addUser(user);
    }

    private void removeCarByPlate() {
        System.out.println("remove car, by plate:");
        String  removePlate = scanner.nextLine();
        ivr.removeVehicle(removePlate);
    }

    private void removeUser() {
        System.out.println("remove user by login:");
        String  removeLogin = scanner.nextLine();
        iur.removeUser(removeLogin);
    }
}