package org.example;

import org.checkerframework.checker.units.qual.C;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Application {
    private VehicleRepository vehicleRepository;
    private UserRepository userRepository;
    private User user;
    private Scanner sc;

    private void updateUser() {
        this.user = User.getInstance();
    }
    public Application() {;
        user = User.getInstance();
        vehicleRepository = VehicleRepository.getInstance();
        userRepository = UserRepository.getInstance();
        sc = new Scanner(System.in);
    }

    public void runApplication() throws IOException {
        readFile();
        userRepository.loadUsers();

        while (true) {
            System.out.println("KOMENDA syntax=komendaINTEGER:");
            String input = sc.nextLine();
            handleCommand(input);
        }
    }

    private void handleCommand(String input) throws IOException {
        if(input.isEmpty()){
            System.out.println("podaj komende");
            return;
        }
        char command = input.charAt(0);
        int ID;
        switch (command) {
            case 'w':
                ID = Integer.parseInt(String.valueOf(input.charAt(1)));
                vehicleRepository.rentCar(ID);
                saveFile();
                break;
            case 'z':
                ID = Integer.parseInt(String.valueOf(input.charAt(1)));
                vehicleRepository.returnCar(ID);
                saveFile();
                break;
            case 'c':
                boolean isLoggedIn = Authentication.getInstance().loggedIn;
                System.out.println(isLoggedIn);
                if (isLoggedIn) {
                    System.out.println(user.toString());
                }
                break;
            case 'l':
                login();
                updateUser();
                break;
            case 'u':
                if(user.getRole().equals("ADMIN")){
                    printAllUsers();
                     break;
                }
                System.out.println("Zle uprawnienia");
                break;
            case 'a':
                System.out.println("Podaj w postaci CSV vehicle");
                String[] line = sc.nextLine().split(";");
                Vehicle vehicle = toVehicle(line);
                if(vehicle == null){
                    System.out.println("Zly TYP POJAZDU");
                    break;
                }
                break;
            case 'r':
                ID = Integer.parseInt(String.valueOf(input.charAt(1)));
                vehicleRepository.removeVehicle(ID);
                break;
            default:
                System.out.println("ZLY INPUT");
                break;
        }
    }

    private void printAllUsers() {
        List<User> userList = userRepository.getAllUsers();
        for (User user : userList){
            System.out.println(user.toString());
        }
    }

    private Vehicle toVehicle(final String[] line) {
        Vehicle vehicle = null;
        try {
            String type = line[0];
            String brand = line[1];
            String model = line[2];
            Integer year = Integer.parseInt(line[3]);
            Integer price = Integer.parseInt(line[4]);
            if (type.equals("Motorcycle")) {
                String category = line[5];
                boolean rented = Boolean.parseBoolean(line[6]);
                vehicle = new Motorcycle(type, brand, model, year, price, category, rented);
                vehicleRepository.save(vehicle);
            } else if (type.equals("Car")) {
                boolean rented = Boolean.parseBoolean(line[5]);
                vehicle = new Car(type,brand,model,year,price,rented);
                vehicleRepository.save(vehicle);
            }
            return vehicle;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    private void login() {
        Authentication auth = Authentication.getInstance();
        if (auth.loggedIn) {
            System.out.println("jestes juz zalogowany");
            return;
        }
        readInputForAuth(auth);
    }

    private void readFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("Brum.txt"));
        try {
            readFileForVehicles(br);
        } finally {
            br.close();
        }
    }

    private void saveFile() throws IOException {
        StringBuilder sb = new StringBuilder();
        List<Vehicle> vehicleList = vehicleRepository.getVehicles();
        for (Vehicle vehicle : vehicleList) {
            sb.append(vehicle.toCSV());
        }
        String str = sb.toString();
        System.out.println(str);
        clearFile();
        writeFile(str);
    }

    private static void writeFile(final String str) throws IOException {
        BufferedWriter fw = new BufferedWriter(new FileWriter("Brum.txt", true));
        try {
            fw.flush();
            fw.write(str);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fw.close();
        }
    }

    private static BufferedWriter clearFile() throws IOException {
        BufferedWriter fw = new BufferedWriter(new FileWriter("Brum.txt", false));
        try {
        } finally {
            fw.close();
        }
        return fw;
    }

    private void readInputForAuth(final Authentication auth) {
        while (true) {
            System.out.println("login SPACJA password");
            String[] credentials = sc.nextLine().split(" ");

            if (credentials.length < 2) {
                System.out.println("ZLY FORMAT");
                return;
            }
            String login = credentials[0];
            String unhashedPassword = credentials[1];
            auth.authenticate(login, unhashedPassword);

            if (auth.loggedIn) {
                System.out.println("ZALOGOWANO! Witaj " + login);
                return;
            }
            System.out.println("złe dane logowania");
        }
    }

    private void readFileForVehicles(final BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null ) {
            String[] values = line.split(";");
            String type = values[0];
            String brand = values[1];
            String model = values[2];
            Integer year = Integer.parseInt(values[3]);
            Integer price = Integer.parseInt(values[4]);

            if (type.equals("Motorcycle")) {
                String category = values[5];
                boolean rented = Boolean.parseBoolean(values[6]);
                Motorcycle motorcycle = new Motorcycle(type, brand, model, year, price, category, rented);
                vehicleRepository.save(motorcycle);
            } else if (type.equals("Car")) {
                boolean rented = Boolean.parseBoolean(values[5]);
                Car car = new Car(type, brand, model, year, price, rented);
                vehicleRepository.save(car);
            }
        }
    }
}