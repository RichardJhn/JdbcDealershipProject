package com.pluralsight.Models;

import com.pluralsight.Contract;

public class SalesContract extends Contract {

    private double salesTax;
    private double recordingFee;
    private double processingFee;
    private  boolean finance;

    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold,
                         boolean finance) {
        super(date, customerName, customerEmail, vehicleSold);
        this.finance = finance;
        this.processingFee = processingFee;
        this.recordingFee = recordingFee;
        this.salesTax = salesTax;
    }

    public double getSalesTax() {
        return salesTax;
    }

    public double getRecordingFee() {
        return recordingFee;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public boolean isFinance() {
        return finance;
    }

    @Override
    public double getTotalPrice(){
        double vehiclePrice = getVehicleSold().getPrice();


        double total = vehiclePrice + (vehiclePrice * .05) + recordingFee + processingFee;

        return total;


    }

    @Override
    public double getMonthlyPayment(){

        double totalPrice = getTotalPrice();
        double annualRate = (totalPrice > 10000.00) ? 0.0425 : 0.0525;
        double monthlyRate = annualRate / 12;
        int months = (totalPrice > 10000) ? 48 : 24;

        //total price times monthly rate * math(+1,monthlyrate and months(being the exp))
        //then divide it by (1+ monthly rate, months behind exp) -1)
        //double monthlyPayment = (totalPrice * monthlyRate * Math.pow(1 + monthlyRate, months))
        //    / (Math.pow(1 + monthlyRate , months)- 1);

        //double numerator = totalPrice * monthlyRate;
        //double demoninator = 1- Math.pow(1 + monthlyRate, -months);
        //double monthlyPayment = (totalPrice * monthlyRate) / (1- Math.pow(1 + monthlyRate, - months));
        double monthlyPayment = (totalPrice / monthlyRate) + (totalPrice * monthlyRate);

        //double rate = (price < 10000 ? 0.425:0.50);

        return 0;
    }
}
