package com.pluralsight.Models;

import java.util.ArrayList;
import java.util.List;


public class Dealership {

    private String name;
    private String address;
    private String phone;
    private ArrayList<Vehicle> inventory = new ArrayList<>();

    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<>();
    }
    public void addVehicle(Vehicle vehicle){
        inventory.add(vehicle);

    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void removeVehicle(Vehicle vehicle){
        inventory.remove(vehicle);
    }

    public String getName() {
        return name;
    }

    public List<Vehicle> getAllVehicles() {
        return inventory;
    }

    public List<Vehicle> getVehicleByPrice(double min, double max) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : inventory) {
            if (v.getPrice() >= min && v.getPrice() <= max) {
                result.add(v);
            }
        }
        return result;
    }

    public List<Vehicle> getVehicleByMakeModel(String make, String model){
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : inventory){
            if (v.getMake().equalsIgnoreCase(make) && v.getModel().equalsIgnoreCase(model)){
                result.add(v);
            }
        }
        return result;
    }

    public List<Vehicle> getVehicleByYear(int min, int max){
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : inventory){
            if (v.getYear() >= min && v.getYear() <= max){
                result.add(v);
            }

        }
        return result;
    }
    public ArrayList<Vehicle> getVehicleByColor(String color){
        ArrayList<Vehicle> result = new ArrayList<>();
        for ( Vehicle v : inventory){
            if (v.getColor().equalsIgnoreCase(color)){
                result.add(v);
            }
        }
        return result;
    }
    public List<Vehicle> getVehicleByMileage(double min, double max){
        List<Vehicle> result = new ArrayList<>();
        for ( Vehicle v : inventory){
            if (v.getOdometer() >= min && v.getOdometer() <= max){
                result.add(v);
            }
        }
        return result;
    }
    public List<Vehicle> getVehicleByType(String type){
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : inventory){
            if (v.getVehicleType().equalsIgnoreCase(type)){
                result.add(v);
            }
        }
        return result;
    }
    public Vehicle getVehicleByVin(String vin){
        for (Vehicle v : inventory){
            if (v.getVin().equals(vin)){
                return v;

            }
        }
        return null;
    }



}