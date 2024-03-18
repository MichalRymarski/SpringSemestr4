import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        VehicleRepository vehicleRepository = new VehicleRepository();
        readFile(vehicleRepository);
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("KOMENDA syntax=komendaINTEGER:");
            String input = sc.nextLine();
            char command = input.charAt(0);
            Integer ID = Integer.parseInt(String.valueOf(input.charAt(1)));
            switch (command){
                case 'w' :
                    vehicleRepository.rentCar(ID);
                    saveFile(vehicleRepository);
                    break;
                case 'z':
                    vehicleRepository.returnCar(ID);
                    saveFile(vehicleRepository);
                    break;
                default:
                    System.out.println("ZLY INPUT");
                    break;
            }
        }
    }

    private static void saveFile(final VehicleRepository vehicleRepository) {
        StringBuilder sb = new StringBuilder();
        List<Vehicle> vehicleList = vehicleRepository.getVehicles();
        for (Vehicle vehicle : vehicleList) {
            sb.append(vehicle.toCSV());
        }
        String str = sb.toString();
        System.out.println(str);
        BufferedWriter fw = null;
        try {
            fw = new BufferedWriter(new FileWriter("Brum.txt", true));
            fw.flush();
            fw.write(str);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void readFile(final VehicleRepository vehicleRepository) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("Brum.txt"));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                if (values[0].equals("Motorcycle")) {
                    String brand = values[1];
                    String model = values[2];
                    Integer year = Integer.parseInt(values[3]);
                    Integer price = Integer.parseInt(values[4]);
                    String category = values[5];
                    Motorcycle motorcycle = new Motorcycle(brand, model, year, price, category);
                    vehicleRepository.save(motorcycle);
                } else if (values[0].equals("Car")) {
                    String brand = values[1];
                    String model = values[2];
                    Integer year = Integer.parseInt(values[3]);
                    Integer price = Integer.parseInt(values[4]);
                    Car car = new Car(brand, model, year, price);
                    vehicleRepository.save(car);
                }
            }
            br.close();
        } finally {

        }
    }
}
