package com.pluralsight.Models;

public class Vehicle {
    int vin;
    int year;
    String make;
    String color;
    String model;
    String vehicleType;
    int odometer;
    double price;
//purely for getters and setters

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getVin() {
        return vin;
    }

    public void setVin(int vin) {
        this.vin = vin;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Vehicle(int vin, int year, String make, String color, String model,
                   String vehicleType, int odometer, double price){
        this.vin = vin;
        this.year = year;
        this.make = make;
        this.color = color;
        this.model = model;
        this.vehicleType = vehicleType;
        this.odometer = odometer;
        this.price = price;

    }

    @Override
    public String toString() {
        return  " ||vin = " + vin + "||" +
                " ||year = " + year + "||" +
                " ||make = " + make  + "||" +
                " ||Color = " + color + "||" +
                " ||model = " + model + "||" +
                " ||vehicleType = " + vehicleType + "||" +
                " ||odometer = " + odometer + "||" +
                " ||price = $" + price + "||" + "\n"+
                "------------------------------------------------------------------------------------------------------------------------------------------------------------ \n";
    }
}