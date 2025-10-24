package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DealershipFileManager {

    public static void getDealership() {
        // Expect rows like: vim|year|make|model|vehicleType|color|odometer|price
        String filename = "inventory.csv";
        Dealership myDealership = new Dealership("Street Dealers", "123 Main St", "121-212-2222");
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String s = line.trim();
                if (s.isEmpty()) continue;

                String[] tokens = s.split("\\|", -1);

                if (tokens.length != 8) {
                    System.err.println("Skipping line: expected 8 fields (vim|year|make|model|vehicleType|color|odometer|price)");
                    continue;
                }
                try {
                    Vehicle car = createVehicle(tokens);
                    myDealership.addVehicle(car);

                } catch (Exception ex) {
                    System.err.println("Skipping line (bad data): " + ex.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: File not found: " + filename);
        } catch (IOException e) {
            System.err.println("I/O error reading " + filename + ": " + e.getMessage());
        }
    }

    private static Vehicle createVehicle(String[] tokens) {
        int vin = Integer.parseInt(tokens[0].trim()); //vim number
        int year = Integer.parseInt(tokens[1].trim()); // Year
        String make = tokens[2].trim(); //make
        String model = tokens[3].trim(); //model
        String vehicleType = tokens[4].trim();
        String color = tokens[5].trim();
        int odometer = Integer.parseInt(tokens[6].trim());
        double price = Double.parseDouble(tokens[7].trim());

        return new Vehicle(vin,year,make,model,vehicleType,color,odometer,price);
    }
}
