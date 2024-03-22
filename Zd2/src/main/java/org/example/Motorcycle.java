package org.example;

public class Motorcycle extends Vehicle {
    final private String category;

    public Motorcycle(final String type,
                      final String brand,
                      final String model,
                      final Integer year,
                      final Integer price,
                      final String category,
                      final boolean rented) {
        super(type, brand, model, year, price,rented);
        this.category = category;
    }

    @Override
    public String toCSV() {
        return type+";"+brand + ";" + model + ";" + year + ";" + price + ";" +  category + ";" +rented + ";" + ID + "\n";
    }


    @Override
    public String toString() {
        return
               ", type='" + type + '\'' +
               ", brand='" + brand + '\'' +
               ", model='" + model + '\'' +
               ", year=" + year +
               ", price=" + price +
               "category='" + category + '\'' +
               ", rented=" + rented +
               ", ID=" + ID +
               '}';
    }
}
