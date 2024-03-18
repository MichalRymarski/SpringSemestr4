
public class Motorcycle extends Vehicle {
    final private String category;

    public Motorcycle(final String brand, final String model, final Integer year, final Integer price, final String category) {
        super(brand, model,year,price);
        this.category= category;
    }

    @Override
    public String toCSV() {
        return brand+";"+model+";"+year+";"+price+";"+rented+";"+ID+";"+category+";"+"\n";
    }

    @Override
    public String toString() {
        return
            "brand='" + brand + '\'' +
            ", model='" + model + '\'' +
            ", year=" + year +
            ", price=" + price +
            ", rented=" + rented +
            ", ID=" + ID +
            ",category="+category;
    }
}
