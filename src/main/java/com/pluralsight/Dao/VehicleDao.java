package com.pluralsight.Dao;
import com.pluralsight.Models.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class VehicleDao {

    private Connection connection;
    private final String url = "jdbc:mysql://localhost:3306/cardealership";
    private final String user = "root";
    private final String password = "yearup";

    // Constructor opens the connection
    public VehicleDao() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot connect to database");
        }
    }
    private Vehicle mapRow(ResultSet rs) throws SQLException {
        return new Vehicle(
                rs.getString("VIN"),
                rs.getInt("Year"),
                rs.getString("Make"),
                rs.getString("Color"),
                rs.getString("Model"),
                rs.getString("Vehicle Type"),
                rs.getInt("Odometer"),
                rs.getDouble("price")
        );
    }

    public List<Vehicle> listAllVehicle() {
        List<Vehicle> vehicles = new ArrayList<>();
        try (
            PreparedStatement preparedStatement = connection.prepareStatement("select *\n" +
                    "from vehicles");
            ResultSet rs = preparedStatement.executeQuery()){

            while(rs.next()){
                vehicles.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.out.println("error");
            e.printStackTrace();
        }
        return vehicles;
    }


}
