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
    public List<Vehicle> byPriceRange(double min, double max) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE price BETWEEN ? AND ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setDouble(1, min);
            preparedStatement.setDouble(2, max);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }
    public List<Vehicle> byMakeModel(String make, String model) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE make = ? and model = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, make);
            preparedStatement.setString(2, model);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }
    public List<Vehicle> byYearRange(double min, double max) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE Year BETWEEN ? AND ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setDouble(1, min);
            preparedStatement.setDouble(2, max);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }
    public List<Vehicle> byColor(String color) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE color = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, color);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }

    public List<Vehicle> byMileage(double min, double max) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE odometer BETWEEN ? AND ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setDouble(1, min);
            preparedStatement.setDouble(2, max);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }

    public List<Vehicle> byType(String vType) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE Vehicle Type = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, vType);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }
    public boolean addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (VIN, Year, Make, Color, Model, `Vehicle Type`, Odometer, price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, vehicle.getVin());
            ps.setInt(2, vehicle.getYear());
            ps.setString(3, vehicle.getMake());
            ps.setString(4, vehicle.getColor());
            ps.setString(5, vehicle.getModel());
            ps.setString(6, vehicle.getVehicleType());
            ps.setInt(7, vehicle.getOdometer());
            ps.setDouble(8, vehicle.getPrice());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    public boolean removeVehicleByVin(String vin) {
        String sql = "DELETE FROM vehicles WHERE VIN = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, vin);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}
