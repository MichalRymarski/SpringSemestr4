import java.util.List;

public interface VehicleRepo {

    public void rentCar(final Integer id);

        public void returnCar(final Integer id);

    public List<Vehicle > getVehicles();

        public void save(Vehicle vehicle);
}
