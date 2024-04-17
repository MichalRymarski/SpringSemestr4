package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dao.IUserRepository;
import org.example.dao.IVehicleRepository;
import org.example.model.User;
import org.example.model.Vehicle;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RentService {

    private final IUserRepository userRepository;

    private final IVehicleRepository vehicleRepository;

    public boolean rentVehicle(String plate, String login) {
        User user = userRepository.getUser(login);
        Vehicle vehicle = vehicleRepository.getVehicle(plate);

        if (user != null && vehicle != null && !vehicle.isRent()) {
            vehicleRepository.rentVehicle(plate,login);
            return true;
        }
        return false;
    }

    public boolean returnVehicle(String plate, String login){
        User user = userRepository.getUser(login);
        Vehicle vehicle = vehicleRepository.getVehicle(plate);
        if(user != null && vehicle != null && vehicle.isRent()){
            vehicleRepository.returnVehicle(plate, login);
            System.out.println("zwrocono pojazd "+plate);
            return true;
        }
        return  false;
    }
}