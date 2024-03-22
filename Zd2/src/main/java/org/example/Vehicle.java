package org.example;

public abstract class Vehicle {
    protected String type;
    protected String brand;
    protected String model;
    protected Integer year;
    protected Integer price;
    protected Boolean rented;

    protected Integer ID;

    protected Vehicle(final String type,
                      final String brand,
                      final String model,
                      final Integer year,
                      final Integer price,
                      final boolean rented
    ) {
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.rented = rented;
        this.ID = 0;
    }

    private Vehicle() {
        type = "";
        brand = "";
        model = "";
        year = 0;
        price = 0;
    }

    public abstract String toCSV();

    public abstract String toString();

    public Integer getID() {
        return ID;
    }

    public void setID(final Integer ID) {
        this.ID = ID;
    }
}
