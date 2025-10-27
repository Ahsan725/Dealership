package com.pluralsight;

import java.io.*;

public class DealershipFileManager {

    private static final String filename = "inventory.csv";

    public static Dealership getDealership() {
        Dealership dealership = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            //Read the first line for dealership meta
            String first = br.readLine();
            if (first == null || first.trim().isEmpty()) {
                throw new IllegalStateException("Inventory file is empty or missing the first line with dealership info");
            }

            String[] header = first.trim().split("\\|", -1);
            if (header.length != 3) {
                throw new IllegalStateException(
                        "First line must be: name|address|phone");
            }

            String name = header[0].trim();
            String address = header[1].trim();
            String phone = header[2].trim();

            dealership = new Dealership(name, address, phone);

            //Read remaining lines for vehicles
            String line;
            while ((line = br.readLine()) != null) {
                String s = line.trim();
                if (s.isEmpty()) continue; // skip blank lines

                String[] tokens = s.split("\\|", -1);
                if (tokens.length != 8) {
                    System.err.println("Skipping line. Expected 8 fields: vin|year|make|model|vehicleType|color|odometer|price");
                    continue;
                }

                try {
                    Vehicle car = createVehicle(tokens);
                    dealership.addVehicle(car);
                } catch (Exception ex) {
                    System.err.println("Skipping line due to bad data: " + ex.getMessage());
                }
            }

        } catch (IOException e) {
            System.err.println("Could not read file " + filename + ": " + e.getMessage());
            // Fallback so your app does not crash if file missing
            if (dealership == null) {
                dealership = new Dealership("Unknown Dealership", "123 Main St", "121-212-2222");
            }
        } catch (IllegalStateException e) {
            System.err.println("Inventory format error: " + e.getMessage());
        }
        return dealership;
    }

    private static Vehicle createVehicle(String[] tokens) {
        int vin = Integer.parseInt(tokens[0].trim());
        int year = Integer.parseInt(tokens[1].trim());
        String make = tokens[2].trim();
        String model = tokens[3].trim();
        String vehicleType = tokens[4].trim();
        String color = tokens[5].trim();
        int odometer = Integer.parseInt(tokens[6].trim());
        double price = Double.parseDouble(tokens[7].trim());

        return new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
    }

    public void saveDealership(Dealership dealership){

        try(BufferedWriter buffWrite = new BufferedWriter(new FileWriter(filename))){
            buffWrite.write(String.format("%-25s| %-25s| %-25s", getDealership(),getDealership().getAddress(),getDealership().getPhone()));
            buffWrite.newLine();

            //Write vehicles
            for(Vehicle v : dealership.getInventory()){
                String formatVehicle = String.format("%-15s| %-5d| %-15s| %-15s| %-10s| %-8s| %-10s| %-10.2f", v.getVin(),v.getYear(),v.getMake(),v.getModel(),v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice());

                buffWrite.write(formatVehicle);
                buffWrite.newLine();
            }
            System.out.println("Inventory updated successfully!");

        }catch(IOException e){
            System.out.println("No file found.");
        }
    }
}
