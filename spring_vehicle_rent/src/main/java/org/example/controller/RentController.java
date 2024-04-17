package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.RentCarDto;
import org.example.service.RentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rents")
@RequiredArgsConstructor
public class RentController {

    private final RentService rentService;

    @PostMapping(value = "/rent")
    public ResponseEntity<String> rentVehicle(@RequestBody RentCarDto request) {
        boolean success = rentService.rentVehicle(request.getPlate(),request.getLogin());
        if (success) {
            return ResponseEntity.ok("Vehicle rented");
        } else {
            return ResponseEntity.badRequest().body("Failed");
        }
    }

    @PostMapping(value =  "/return")
    public ResponseEntity<String> returnVehicle(@RequestBody RentCarDto request){
        boolean success = rentService.returnVehicle(request.getPlate(),request.getLogin());
        if(success){
            return ResponseEntity.ok(request.getPlate()+"got returned");
        }
        return  ResponseEntity.badRequest().body("Failed to return "+request.getPlate());
    }

}