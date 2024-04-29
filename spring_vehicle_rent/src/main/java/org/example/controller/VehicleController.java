package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.DeleteState;
import org.example.dto.VehicleDto;
import org.example.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping(value = "/add")
    public ResponseEntity<String> addVehicle(@RequestBody VehicleDto vehicle) {
        String brand = vehicle.getBrand();
        String model = vehicle.getModel();
        int year = vehicle.getYear();
        double price = vehicle.getPrice();
        String plate = vehicle.getPlate();
        String category = null;
        boolean success = false;

        if (vehicle.getCategory() != null) {
            category = vehicle.getCategory();
            success = vehicleService.createVehicle(brand, model, year, price, plate, category);
        }else{
            success = vehicleService.createVehicle(brand, model, year, price, plate);
        }

        if (success){
            return ResponseEntity.ok("dodano");
        }
        return ResponseEntity.badRequest().body("nie dodano");
    }

    @GetMapping(value = "/get/{vehiclePlate}")
    public ResponseEntity<VehicleDto> getByPlate(@PathVariable String vehiclePlate) {
        VehicleDto vehicle = vehicleService.getVehicle(vehiclePlate);
        if (vehicle != null) {
            return ResponseEntity.ok(vehicle);
        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<VehicleDto>> getAllVehicles() {
        List<VehicleDto> vehicleDtoList = vehicleService.getVehicles().stream().toList();
        if (vehicleDtoList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vehicleDtoList);
    }

    @DeleteMapping(value = "/delete/{vehiclePlate}")
    public ResponseEntity<String> deleteVehicle(@PathVariable String vehiclePlate){
        DeleteState state = vehicleService.deleteVehicle(vehiclePlate);
        switch (state) {
            case NOT_FOUND -> {
                return ResponseEntity.notFound().build();
            }
            case RENTED -> {
                return ResponseEntity.badRequest().body("Vehicle is currently rented");
            }
            default -> {
                return ResponseEntity.ok("Vehicle got deleted");
            }
        }
    }


}
