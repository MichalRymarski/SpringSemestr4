package org.example.service;

import lombok.RequiredArgsConstructor;
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
            boolean rent = false;
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
        if (vehicle != null && vehicle instanceof Motorcycle motorcycle) {
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
        VehicleDto vehicleDto = VehicleDto.builder()
            .brand(brand)
            .model(model)
            .year(year)
            .price(price)
            .plate(plate)
            .rent(rent)
            .build();
        return vehicleDto;
    }

    private static VehicleDto mapToVehicleDto(final String plate, final Motorcycle motorcycle) {
        String brand = motorcycle.getBrand();
        String model = motorcycle.getModel();
        int year = motorcycle.getYear();
        double price = motorcycle.getPrice();
        boolean rent = false;
        String category = motorcycle.getCategory();
        VehicleDto vehicleDto = VehicleDto.builder()
            .brand(brand)
            .model(model)
            .year(year)
            .price(price)
            .plate(plate)
            .rent(rent)
            .category(category)
            .build();
        return vehicleDto;
    }

    public void createVehicle(final String brand,
                              final String model,
                              final int year,
                              final double price,
                              final String plate) {
        Car car = new Car(brand, model, year, price, plate);
        boolean success = vehicleRepository.addVehicle(car);
        if (success) {
            System.out.println("DODANO POJAZD");
        } else {
            System.out.println("NIE UDALO SIE DODAC POJAZDU");
        }
    }

    public void createVehicle(final String brand,
                              final String model,
                              final int year,
                              final double price,
                              final String plate,
                              final String category) {
        Motorcycle motorcycle = new Motorcycle(brand, model, year, price, plate, category);
        boolean success = vehicleRepository.addVehicle(motorcycle);
        if (success) {
            System.out.println("DODANO POJAZD");
        } else {
            System.out.println("NIE UDALO SIE DODAC POJAZDU");
        }
    }

    public String deleteVehicle(String plate) {
        Vehicle vehicle = vehicleRepository.getVehicle(plate);
        if (vehicle == null) {
            return "vehicle not found";
        } else if (vehicle.isRent()) {
            return "vehicle is rented cant delete";
        } else {
            vehicleRepository.removeVehicle(plate);
        }
        return "deleted";
    }
}
