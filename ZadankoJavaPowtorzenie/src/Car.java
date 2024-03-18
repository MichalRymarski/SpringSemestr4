
public class Car extends Vehicle{

    public Car(final String brand, final String model, final Integer year, final Integer price) {
        super(brand, model,year,price);
    }

    @Override
    public String toCSV() {
        return brand+";"+model+";"+year+";"+price+";"+rented+";"+ID+";"+"\n";
    }

    @Override
    public String toString() {
        return
               "brand='" + brand + '\'' +
               ", model='" + model + '\'' +
               ", year=" + year +
               ", price=" + price +
               ", rented=" + rented +
               ", ID=" + ID;
    }
}
