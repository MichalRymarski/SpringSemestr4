package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dao.IVehicleRepository;
import org.example.dto.VehicleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/api/vehicle")
@RequiredArgsConstructor
public class VehicleController {
    private final vehicle

    @PostMapping(value = "/add")
    public ResponseEntity<String> addVehicle(@RequestBody VehicleDto vehicle){
         String brand = vehicle.getBrand();
         String model = vehicle.getModel();
         int year = vehicle.getYear();
         double price = vehicle.getPrice();
         String plate = vehicle.getPlate();
         boolean rent = false;
        if(vehicle.getCategory() != null){
            String category = vehicle.getCategory();
        }
    }
}
