package org.example;

public class Car extends Vehicle {

    public Car(final String type,
               final String brand,
               final String model,
               final Integer year,
               final Integer price,
               final boolean rented) {
        super(type, brand, model, year, price, rented);
    }

    @Override
    public String toCSV() {
        return type+";"+brand + ";" + model + ";" + year + ";" + price + ";" + rented + ";" + ID + ";" + "\n";
    }


    @Override
    public String toString() {
        return
               "type='" + type + '\'' +
               ", brand='" + brand + '\'' +
               ", model='" + model + '\'' +
               ", year=" + year +
               ", price=" + price +
               ", rented=" + rented +
               ", ID=" + ID +
               '}';
    }
}
