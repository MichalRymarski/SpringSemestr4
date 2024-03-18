public abstract class Vehicle {
    protected String brand;
    protected String model;
    protected Integer year;
    protected Integer price;
    protected Boolean rented;

    protected Integer ID;

    protected Vehicle(final String brand,
                    final String model,
                    final Integer year,
                    final Integer price
    ) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.rented = false;
        this.ID = 0;
    }

    private Vehicle() {
        brand = "";
        model = "";
        year = 0;
        price = 0;
        rented = false;
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
