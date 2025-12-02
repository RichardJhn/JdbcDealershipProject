package com.pluralsight;

public class LeaseContract extends Contract {

    private double endValue;
    private double leaseFee;

    public LeaseContract(String date, String customerName, String customerEmail,
                         Vehicle vehicleSold) {
        super(date, customerName, customerEmail, vehicleSold);
        this.endValue = endValue = vehicleSold.getPrice() * 0.5; // 50% of original value
        this.leaseFee = leaseFee = vehicleSold.getPrice() * 0.07; // The 7% lease fee
    }

    public double getEndValue() {
        return endValue;
    }

    public double getLeaseFee() {
        return leaseFee;
    }
    @Override
    public double getTotalPrice(){


        return getVehicleSold().getPrice() + leaseFee;

    }

    @Override
    public double getMonthlyPayment(){

        double totalPrice = getTotalPrice();
        double annualRate = .04;
        double monthlyRate = annualRate / 12;
        int months = 36;
        double monthlyPayment = (totalPrice / months) + (totalPrice * monthlyRate);

        return monthlyPayment;
    }
}
