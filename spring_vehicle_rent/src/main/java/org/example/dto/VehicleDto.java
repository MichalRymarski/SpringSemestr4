package org.example.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VehicleDto {
    private String brand;
    private String model;
    private int year;
    private double price;
    private String plate;
    private boolean rent;
    private String category;

}
