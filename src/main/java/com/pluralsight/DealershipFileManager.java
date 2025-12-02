package com.pluralsight;
import java.io.*;

public class DealershipFileManager {


    public Dealership getDealership(){
        Dealership dealership = null;
//        System.out.printf("Dealership: %s", Dealership[0]);
        //code to read the inventory.csv file
        //to do : figure out how to sow dealership information

        try(BufferedReader br = new BufferedReader(new FileReader("inventory.csv"))) {
            // ^Open's file to read

            // Read the first line - this holds dealership info
            String firstLine = br.readLine();

            if (firstLine != null) {
                // split bit pipe and trim
                String[] parts = firstLine.split("\\|");
                String name = parts[0].trim();
                String address = parts[1].trim();
                String number = parts[2].trim();

                // Creating the dealership object with that info now
                dealership = new Dealership(name, address, number);
            }

//            dealership = new Dealership(parts[0], parts[1], parts[2]);

            // makes the rest of the lines read for each vehicle
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                int vin = Integer.parseInt(data[0]);
                int year = Integer.parseInt(data[1]);
                String make = data[2];
                String color = data[3];
                String model = data[4];
                String vehicleType = data[5];
                int odometer = Integer.parseInt(data[6]);
                double price = Double.parseDouble(data[7]);

                //command to print out all vehicles

                Vehicle v = new Vehicle(vin, year, make,color, model, vehicleType, odometer, price);
                dealership.addVehicle(v);
            }


        } catch (IOException e){
            System.out.println("Error reading file");
        }
        return  dealership;
    }


    public void saveDealership(Dealership dealership){
        try{ FileWriter filewriter = new FileWriter("inventory.csv");
            BufferedWriter bufferedWriter = new BufferedWriter(filewriter);
            for (Vehicle v : dealership.getAllVehicles()){
                bufferedWriter.write(v.getVin()+ "|" +
                        v.getYear() + "|" +
                        v.getMake() + "|" +
                        v.getColor() + "|" +
                        v.getModel() + "|" +
                        v.getVehicleType() + "|" +
                        v.getOdometer() + "|" +
                        v.getPrice() + "|");
                bufferedWriter.newLine();

            }
            bufferedWriter.close();

        }catch (Exception e){
            System.out.println("error saving dealership");

        }

    }
}