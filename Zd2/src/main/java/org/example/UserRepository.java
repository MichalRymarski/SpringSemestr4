package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements UserRepo {
    private static UserRepository instance = null;
    private final List<User> userList;
    private VehicleRepository vehicleRepository;
    private User user;

    private UserRepository() {
        this.user = User.getInstance();
        this.userList = new ArrayList<>();
        this.vehicleRepository = VehicleRepository.getInstance();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public void changeCurrentCar(final Integer id, final Vehicle vehicle) {
        if (vehicle.rented == false) {
            vehicle.rented = true;
            if(user.getVehicleID() != 0){
                int previousID = user.getVehicleID();
                vehicleRepository.returnCar(previousID);
                user.setVehicleID(id);
                return;
            }
            user.setVehicleID(id);
        }
    }

    @Override
    public User getUser(int ID) {
        return userList.stream()
            .filter(user -> user.getVehicleID() == ID)
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userList;
    }

    @Override
    public void loadUsers() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("Users.txt"));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                if(values.length <4){
                    break;
                }
                int ID = Integer.parseInt(values[3]);
                String role = values[2];
                String hashedPass = values[1];
                String login= values[0];
                User newUser = new User(login,hashedPass,role,ID );
                userList.add(newUser);
            }
        } finally {
            br.close();
        }
    }
}