package com.pluralsight;
import com.pluralsight.Dao.VehicleDao;
import com.pluralsight.Models.Dealership;
import com.pluralsight.Models.LeaseContract;
import com.pluralsight.Models.SalesContract;
import com.pluralsight.Models.Vehicle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.time.*;
public class UserInterface{

    private DealershipFileManager dealershipFileManager = new DealershipFileManager();
    private Dealership dealership = dealershipFileManager.getDealership();
    private ArrayList<Vehicle> inventory = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    VehicleDao dao = new VehicleDao();



    public void displayScreen() {

        //  DealershipFileManager dealershipFileManager = new DealershipFileManager();
        //  Dealership dealership = dealershipFileManager.loadDealership("inventory.csv");

        init();
        String choice = "";

        while (!choice.equalsIgnoreCase("99")){
            System.out.println("""
                    1 - Find vehicles withing price range
                    2 - Find vehicles by make/model
                    3 - Find Vehicles by year range
                    4 - Find vehicles by color
                    5 - Find vehicles by mileage range
                    6 - Find vehicles by type (car, truck, SUV, van)
                    7 - List ALL vehicles
                    8 - Add a vehicle
                    9 - Remove a vehicle
                    11 - Move to contract screen
                    99 - Quit""");

            System.out.println("Enter your choice:");

            choice = scanner.nextLine().trim();

            //here we code the options

            try (BufferedReader reader = new BufferedReader(new FileReader("inventory.csv"))){

                switch(choice.toString()){
                    case "1":
                        findByPriceRange();
                        break;
                    case "2":
                        findByMakeModel();
                        break;
                    case "3":
                        findByYear();
                        break;
                    case "4":
                        findByColor();
                        break;
                    case "5":
                        findByMileage();
                        break;
                    case "6":
                        findByType();
                        break;
                    case "7":
                        List<Vehicle> list = dao.listAllVehicle();
                        for (Vehicle v : list) {
                            System.out.println(v);
                        }
                        break;
                    case "8":
                        addVehicle();
                        break;
                    case "9":
                        removeVehicle();
                        break;
                    case "99":
                        System.out.println("quitting");
                        return;
                    case "11":
                        contractMenu();
                        break;
                    default:
                        System.out.println("Invalid entry! Please try again!");
                }
            }catch(Exception e){
                e.printStackTrace();

            }
        }

    }
    // Initialize dealership from file
    private void init() {
        // creates and loads an object that can read/write the inventory file.
        DealershipFileManager fileManager = new DealershipFileManager();
        dealership = fileManager.getDealership();

        System.out.println("Loaded Dealership: " +
                dealership.getName() + " " +
                dealership.getAddress() + " " +
                dealership.getPhone());
    }

    private void proccessRemoveVehicle() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("what is the vin number of the vehicle you would like to remove?(5 numbers)");
        Vehicle toRemove = null; // empty until found a matching VIN to remove
        int vin = Integer.parseInt(scanner.nextLine());
        ArrayList<Vehicle> inventory = new ArrayList<>();

        for (Vehicle v : dealership.getAllVehicles()) {
            if (v.getVin().equals(vin)) {
                //    inventory.remove(v);
                toRemove = v;
                break;
            }
        }

        if (toRemove != null) {
            // remove the vehicle
            dealership.getAllVehicles().remove(toRemove);

            // save the updated dealership to CSV.
            DealershipFileManager dfm = new DealershipFileManager();
            dfm.saveDealership(dealership);
            System.out.println("Vehicle has been removed ");

        }
        else {
            System.out.println("Vehicle with vin: " + vin + " not found.");
        }
    }
    private void addNewVehicle() {
        try{
            Scanner scanner = new Scanner(System.in);
            FileWriter myWriter = new FileWriter("inventory.csv", true);

            System.out.println("What is the vin number? (5 numbers)");
            String vin = scanner.nextLine();

            System.out.println("What is the year?");
            int year = scanner.nextInt();
            scanner.nextLine();

            System.out.println("What is the make?");
            String make = scanner.nextLine();

            System.out.println("What is the color?");
            String color = scanner.nextLine();

            System.out.println("what is the model?");
            String model = scanner.nextLine();

            System.out.println("What is the vehicle type?");
            String vehicleType = scanner.nextLine();

            System.out.println("What is the mileage?");
            int mileage = scanner.nextInt();
            scanner.nextLine();

            System.out.println("How much are you selling it for?");
            double price = scanner.nextDouble();
            scanner.nextLine();

            Vehicle newVehicle = new Vehicle(vin, year, make, color, model,vehicleType,mileage,price);
            dealership.addVehicle(newVehicle);
            myWriter.write(String.format("" +
                            "\n%s|%s|%s|%s|%s|%s|%s|%.2f",
                    vin, year, make, color,model,vehicleType, mileage, price));
            myWriter.close();
            System.out.println("Vehicle added successfully!");

        } catch (IOException e) {
            System.out.println("Error while adding vehicle");

        }

    }
    private void contractMenu(){
        System.out.println("Contract Menu");
        System.out.println("1) sell Vehicle");
        System.out.println("2) Lease Vehicle");
        System.out.println("3) Return to main menu");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                sale();
                break;
            case "2":
                lease();
            case "3":
                System.out.println("returning to main menu");
                break;
            default:
                System.out.println("error");
                break;
        }

    }
    private void sale(){
        System.out.println("Enter your name:");
        String name = scanner.nextLine();

        System.out.println("Enter your email");
        String email = scanner.nextLine();

        System.out.println("Enter the VIN number of the vehicle:");
        String vin = scanner.nextLine().trim();
        Vehicle vehicle = dealership.getVehicleByVin(vin);

        System.out.println("Will the customer be financing the vehicle? (Y/N)");

        boolean isFinanced = scanner.nextLine().equalsIgnoreCase("Y");

        SalesContract sale = new SalesContract(LocalDate.now().toString(), name, email, vehicle, isFinanced);

        //saving
        ContractDataManager dataManager = new ContractDataManager();
        dataManager.saveContract(sale);
        //removing from inventory
        dealership.removeVehicle(vehicle);
        DealershipFileManager dfm = new DealershipFileManager();
        dfm.saveDealership(dealership);
        dealership.removeVehicle(vehicle);
        System.out.println("Sale has been processed successfully");

    }



    private void lease(){
        System.out.println("Enter your name:");
        String name = scanner.nextLine();

        System.out.println("Enter your email");
        String email = scanner.nextLine();

        System.out.println("Enter the VIN number of the vehicle:");
        String vin = scanner.nextLine().trim();
        Vehicle vehicle = dealership.getVehicleByVin(vin);

        LeaseContract lease = new LeaseContract(LocalDate.now().toString(), name, email, vehicle);

        ContractDataManager dataManager = new ContractDataManager();
        dataManager.saveContract(lease);
        dealership.removeVehicle(vehicle);
        DealershipFileManager dfm = new DealershipFileManager();
        dfm.saveDealership(dealership);


        System.out.println("Lease contract has been processed successfully");

    }

    public void findByPriceRange(){
        System.out.println("what is your minimum budget?");
        double minBudget = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("What is your Maximum budget?");
        double maxBudget = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Here are the vehicles in your budget:");
        List<Vehicle> vehiclesInRange =dao.byPriceRange(minBudget, maxBudget);
        if(vehiclesInRange.isEmpty()){
            System.out.println("No vehicles in your price range");
        }
        else{
            for (Vehicle vehicle : vehiclesInRange) {
            System.out.println(vehicle);
        }

        }

    }
    public void findByMakeModel(){
        System.out.println("What Model?");
        String userModel = scanner.nextLine();
        System.out.println("What make?");
        String userMake = scanner.nextLine();
        System.out.println("Here are the vehicles");
        List<Vehicle> vehiclesMakeModel = dao.byMakeModel(userMake, userModel);
        if(vehiclesMakeModel.isEmpty()){
            System.out.println("No vehicles in inventory");
        }
        else{
            for(Vehicle vehicle : vehiclesMakeModel){
                System.out.println(vehicle);
            }
        }
    }
    public void findByYear(){
        System.out.println("What minimum Year?");
        int minYear = scanner.nextInt();
        scanner.nextLine();
        System.out.println("What maximum Year?");
        int maxYear = scanner.nextInt();
        System.out.println("Here are the vehicles");
        List<Vehicle> vehiclesYear = dao.byYearRange(minYear,maxYear);
        if(vehiclesYear.isEmpty()){
            System.out.println("No vehicles in inventory");
        }
        else{
            for(Vehicle vehicle : vehiclesYear){
                System.out.println(vehicle);
            }
        }

    }
    public void findByColor(){
        System.out.println("What color?");
        String userColor = scanner.nextLine();
        List<Vehicle> vehicleColor = dao.byColor (userColor);
        if(vehicleColor.isEmpty()){
            System.out.println("No vehicles in inventory");
        }
        else{
            for(Vehicle vehicle : vehicleColor){
                System.out.println(vehicle);
            }
        }

    }
    public void findByMileage(){
        System.out.println("What minimum Miles?");
        int minMiles = scanner.nextInt();
        scanner.nextLine();
        System.out.println("What maximum Mile?s");
        int maxMiles = scanner.nextInt();
        System.out.println("Here are the vehicles");
        List<Vehicle> vehiclesMiles = dao.byMileage(minMiles,maxMiles);
        if(vehiclesMiles.isEmpty()){
            System.out.println("No vehicles in inventory");
        }
        else{
            for(Vehicle vehicle : vehiclesMiles){
                System.out.println(vehicle);
            }
        }

    }
    public void findByType(){
        System.out.println("What car type?");
        String userType = scanner.nextLine();
        List<Vehicle> vehicleType = dao.byType (userType);
        if(vehicleType.isEmpty()){
            System.out.println("No vehicles in inventory");
        }
        else{
            for(Vehicle vehicle : vehicleType){
                System.out.println(vehicle);
            }
        }

    }
    private void addVehicle() {
        try {
            System.out.println("Enter VIN (5 numbers):");
            String vin = scanner.nextLine();

            System.out.println("Enter Year:");
            int year = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter Make:");
            String make = scanner.nextLine();

            System.out.println("Enter Model:");
            String model = scanner.nextLine();

            System.out.println("Enter Color:");
            String color = scanner.nextLine();

            System.out.println("Enter Vehicle Type:");
            String vehicleType = scanner.nextLine();

            System.out.println("Enter Mileage:");
            int mileage = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter Price:");
            double price = Double.parseDouble(scanner.nextLine());

            Vehicle vehicle = new Vehicle(vin, year, make, color, model, vehicleType, mileage, price);

            boolean success = dao.addVehicle(vehicle);

            if (success) {
                System.out.println("Vehicle added successfully!");
            } else {
                System.out.println("Failed to add vehicle. Check database connection.");
            }

        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
            e.printStackTrace();
        }
    }
    private void removeVehicle() {
        try {
            System.out.println("Enter the VIN of the vehicle you want to remove:");
            String vin = scanner.nextLine().trim();

            boolean success = dao.removeVehicleByVin(vin);

            if (success) {
                System.out.println("Vehicle removed successfully!");
            } else {
                System.out.println("Vehicle with VIN " + vin + " not found.");
            }
        } catch (Exception e) {
            System.out.println("Error removing vehicle. Please try again.");
            e.printStackTrace();
        }
    }


    //Have the Menu here

}
