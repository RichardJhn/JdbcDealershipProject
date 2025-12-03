package com.pluralsight;
import com.pluralsight.Models.LeaseContract;
import com.pluralsight.Models.SalesContract;

import java.io.*;

public class ContractDataManager {

    public void saveContract(Contract contract) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Contract.csv", true))) {
            String line = "";
            // this is to see if the contract is a lease contract
            if (contract instanceof LeaseContract){
                LeaseContract lease = (LeaseContract) contract;
                //setting up the format for what it will be printing out
                line = String.format("Lease|%s|%s|%s|%s|%.2f|%.2f|",
                        lease.getDate(),
                        lease.getCustomerName(),
                        lease.getCustomerEmail(),
                        lease.getVehicleSold(),
                        lease.getTotalPrice(),
                        lease.getMonthlyPayment());

            }
            else if (contract instanceof SalesContract){
                SalesContract sale = (SalesContract) contract;

                //adding format for the sales contract

                line = String.format("Sale|%s|%s|%s|%s|%.2f|%.2f|",
                        sale.getDate(),
                        sale.getCustomerName(),
                        sale.getCustomerEmail(),
                        sale.getVehicleSold(),
                        sale.getTotalPrice(),
                        sale.getMonthlyPayment());

            }
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            bufferedWriter.close();
            System.out.println("Contract Saved: " + line);


        } catch (IOException e) {
            System.out.println("Error");
        }
    }



}