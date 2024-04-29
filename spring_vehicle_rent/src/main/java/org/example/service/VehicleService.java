package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.DeleteState;
import org.example.dao.IVehicleRepository;
import org.example.dto.VehicleDto;
import org.example.model.Car;
import org.example.model.Motorcycle;
import org.example.model.Vehicle;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final IVehicleRepository vehicleRepository;


    public Collection<VehicleDto> getVehicles() {
        Collection<VehicleDto> vehicleDtos = new ArrayList<>();
        Collection<Vehicle> vehicles = vehicleRepository.getVehicles();
        for (Vehicle vehicle : vehicles) {
            String brand = vehicle.getBrand();
            String model = vehicle.getModel();
            int year = vehicle.getYear();
            double price = vehicle.getPrice();
            String plate = vehicle.getPlate();
            boolean rent = vehicle.isRent();
            String category = null;
            if (vehicle instanceof Motorcycle motorcycle) {
                category = motorcycle.getCategory();
            }
            VehicleDto vehicleDto = VehicleDto.builder()
                .brand(brand)
                .model(model)
                .year(year)
                .price(price)
                .plate(plate)
                .rent(rent)
                .category(category)
                .build();

            vehicleDtos.add(vehicleDto);
        }
        return vehicleDtos;
    }

    public VehicleDto getVehicle(String plate) {
        System.out.println("pass plate");
        Vehicle vehicle = vehicleRepository.getVehicle(plate);
        if (vehicle instanceof Motorcycle motorcycle) {
            return mapToVehicleDto(plate, motorcycle);
        } else if (vehicle != null) {
            return mapToVehicleDto(plate, vehicle);
        } else {
            System.out.println("nie istnieje taki pojazd");
            return null;
        }
    }

    private static VehicleDto mapToVehicleDto(final String plate, final Vehicle vehicle) {
        String brand = vehicle.getBrand();
        String model = vehicle.getModel();
        int year = vehicle.getYear();
        double price = vehicle.getPrice();
        boolean rent = false;
        return VehicleDto.builder()
            .brand(brand)
            .model(model)
            .year(year)
            .price(price)
            .plate(plate)
            .rent(rent)
            .build();
    }

    private static VehicleDto mapToVehicleDto(final String plate, final Motorcycle motorcycle) {
        String brand = motorcycle.getBrand();
        String model = motorcycle.getModel();
        int year = motorcycle.getYear();
        double price = motorcycle.getPrice();
        boolean rent = false;
        String category = motorcycle.getCategory();
        return VehicleDto.builder()
            .brand(brand)
            .model(model)
            .year(year)
            .price(price)
            .plate(plate)
            .rent(rent)
            .category(category)
            .build();
    }

    public boolean createVehicle(final String brand,
                              final String model,
                              final int year,
                              final double price,
                              final String plate) {
        Car car = new Car(brand, model, year, price, plate);
        boolean success = vehicleRepository.addVehicle(car);
        if (success) {
            return true;
        } else {
            return false;
        }
    }

    public boolean createVehicle(final String brand,
                              final String model,
                              final int year,
                              final double price,
                              final String plate,
                              final String category) {
        Motorcycle motorcycle = new Motorcycle(brand, model, year, price, plate, category);
        boolean success = vehicleRepository.addVehicle(motorcycle);
        if (success) {
            return true;
        } else {
            return false;
        }
    }

    public DeleteState deleteVehicle(String plate) {
        Vehicle vehicle = vehicleRepository.getVehicle(plate);
        if (vehicle == null) {
            return DeleteState.NOT_FOUND;
        } else if (vehicle.isRent()) {
            return DeleteState.RENTED;
        } else {
            vehicleRepository.removeVehicle(plate);
        }
        return DeleteState.DELETED;
    }

}
