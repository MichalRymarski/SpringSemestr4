import java.util.ArrayList;
import java.util.List;

public class VehicleRepository implements VehicleRepo {
    private List<Vehicle> vehicleList;
    private int counter ;

    public VehicleRepository() {
        this.vehicleList = new ArrayList<>();
        this.counter = 1;
    }


    @Override
    public void  rentCar(final Integer id) {
        Vehicle vehicle = vehicleList.stream()
            .filter(vec-> vec.getID().equals(id))
            .findFirst()
            .orElse(null);
        if(vehicle!=null){
            if(vehicle.rented == true){
                System.out.println("OUT OF STOCK");
                return;
            }
            vehicle.rented = true;
        }
    }

    @Override
    public void returnCar(final Integer id) {
        Vehicle vehicle = vehicleList.stream()
            .filter(vec-> vec.getID().equals(id))
            .findFirst()
            .orElse(null);
        if(vehicle!=null){
            if(vehicle.rented == false){
                System.out.println("ALREADY RETURNED");
                return;
            }
            vehicle.rented = false;
        }
    }

    @Override
    public List<Vehicle> getVehicles() {
        return vehicleList;
    }

    @Override
    public void save(Vehicle vehicle) {
        vehicle.setID(counter);
        counter++;
        vehicleList.add(vehicle);
    }
}
