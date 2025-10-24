package com.pluralsight;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserInterface {
    private static final Scanner scanner = new Scanner(System.in);
    private static Dealership dealership;

    public static void display() {
        init();
        int operation = -1;
        while (operation != 99) {
            printMenu();
            operation = Integer.parseInt(readInputRequireType("int"));
            switch (operation) {
                case 1 -> processPriceRangeRequest();
                case 2 -> processMakeModelRequest();
                case 3 -> processYearRangeRequest();
                case 4 -> processColorRequest();
                case 5 -> processMileageRangeRequest();
                case 6 -> processTypeRequest();
                case 7 -> processAllVehiclesRequest();
                case 8 -> processAddVehicleRequest();
                case 9 -> processRemoveVehicleRequest();
                case 99 -> System.out.println("System is exiting...");
                default -> System.out.println("Invalid operation... Try again or press 0 to quit");
            }
        }
    }

    private static void processRemoveVehicleRequest() {
        String userChoice = "N";
        System.out.println("Enter any details of the car you want to use to locate the car for deletion: ");
        String userInput = scanner.nextLine();
        boolean deletionStatus = false;

        for (var car : dealership.getAllVehicles()) {
            // Convert numeric fields to String for comparison
            String vinAsString = String.valueOf(car.getVin());
            String yearAsString = String.valueOf(car.getYear());
            String odometerAsString = String.valueOf(car.getOdometer());
            // Convert double price to String
            String priceAsString = String.valueOf(car.getPrice());

            if (userInput.equalsIgnoreCase(car.getMake()) ||
                    userInput.equalsIgnoreCase(car.getModel()) ||
                    userInput.equalsIgnoreCase(car.getVehicleType()) ||
                    userInput.equalsIgnoreCase(car.getColor()) ||
                    userInput.equalsIgnoreCase(vinAsString) ||
                    userInput.equalsIgnoreCase(yearAsString) ||
                    userInput.equalsIgnoreCase(odometerAsString) ||
                    userInput.equalsIgnoreCase(priceAsString)
            ) {
                System.out.println("Is this the car you want to remove?: ");
                printFormatted(car);
                userChoice = scanner.nextLine();
                if (!userChoice.isEmpty() && Character.toLowerCase(userChoice.charAt(0)) == 'y') {
                    //delete
                    dealership.removeVehicle(car);
                    deletionStatus = true;
                    System.out.println("Deletion successful!");
                } else {
                    System.out.println("Deletion cancelled...");
                }
            }
        }
        if (!deletionStatus) {
            System.out.println("No cars marked for deletion!");
        }

    }

    private static void processAddVehicleRequest() {
        int vin;
        int year;
        String make;
        String model;
        String vehicleType;
        String color;
        int odometer;
        double price;
        System.out.println("Enter the vin number: ");
        vin = Integer.parseInt(readInputRequireType("int"));
        System.out.println("Enter the year: ");
        year = Integer.parseInt(readInputRequireType("int"));
        System.out.println("Enter the make number: ");
        make = readInputRequireType("string");
        System.out.println("Enter the model number: ");
        model = readInputRequireType("string");
        System.out.println("Enter the vehicle type: ");
        vehicleType = readInputRequireType("string");
        System.out.println("Enter the color: ");
        color = readInputRequireType("string");
        System.out.println("Enter the odometer number: ");
        odometer = Integer.parseInt(readInputRequireType("int"));
        System.out.println("Enter the price: ");
        price = Double.parseDouble(readInputRequireType("double"));

        Vehicle car = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
        dealership.addVehicle(car);
        System.out.println("Car Added successfully!");
    }

    private static void processTypeRequest() {
        String type;
        System.out.println("Enter the type: ");
        type = readInputRequireType("string");
        boolean anyFound = false;

        for (var car : dealership.getAllVehicles()) {
            if (car.getVehicleType().toLowerCase().contains(type)) {
                printFormatted(car);
                anyFound = true;
            }
        }
        if (!anyFound) {
            System.out.println("No cars matched that type!");
        }
    }

    private static void processAllVehiclesRequest() {
        boolean anyFound = false;
        for (var car : dealership.getAllVehicles()) {
            printFormatted(car);
            anyFound = true;
        }
        if (!anyFound) {
            System.out.println("No car available in the inventory!");
        }
    }

    private static void processMileageRangeRequest() {
        int minRange;
        int maxRange;
        System.out.println("Enter the min mile range: ");
        minRange = Integer.parseInt(readInputRequireType("int"));
        System.out.println("Enter the max mile range: ");
        maxRange = Integer.parseInt(readInputRequireType("int"));
        //swap if the user makes a mistake
        minRange = Math.min(minRange, maxRange);
        maxRange = Math.max(minRange, maxRange);
        boolean anyFound = false;

        for (var car : dealership.getAllVehicles()) {
            if (car.getOdometer() >= minRange && car.getOdometer() <= maxRange) {
                printFormatted(car);
                anyFound = true;
            }
        }
        if (!anyFound) {
            System.out.println("No cars matched that mile range!");
        }
    }

    private static void processColorRequest() {
        String color;
        System.out.println("Enter the color: ");
        color = readInputRequireType("string");
        boolean anyFound = false;

        for (var car : dealership.getAllVehicles()) {
            if (car.getColor().equalsIgnoreCase(color)) {
                printFormatted(car);
                anyFound = true;
            }
        }
        if (!anyFound) {
            System.out.println("No cars matched that color!");
        }
    }

    private static void processYearRangeRequest() {
        int minRange;
        int maxRange;
        System.out.println("Enter the earliest year in the range: ");
        minRange = Integer.parseInt(readInputRequireType("int"));
        System.out.println("Enter the latest year in the range: ");
        maxRange = Integer.parseInt(readInputRequireType("int"));
        //swap if the user makes a mistake
        minRange = Math.min(minRange, maxRange);
        maxRange = Math.max(minRange, maxRange);
        boolean anyFound = false;

        for (var car : dealership.getAllVehicles()) {
            if (car.getYear() >= minRange && car.getYear() <= maxRange) {
                printFormatted(car);
                anyFound = true;
            }
        }
        if (!anyFound) {
            System.out.println("No cars matched that year range!");
        }
    }

    private static void processMakeModelRequest() {
        String makeModel;
        System.out.println("Enter the make/model of the car: ");
        makeModel = readInputRequireType("string");
        boolean anyFound = false;

        for (var car : dealership.getAllVehicles()) {
            if (car.getMake().toLowerCase().contains(makeModel.toLowerCase()) || car.getModel().toLowerCase().contains(makeModel.toLowerCase())) {
                printFormatted(car);
                anyFound = true;
            }
        }
        if (!anyFound) {
            System.out.println("No cars matched that make/model range!");
        }
    }

    private static void processPriceRangeRequest() {
        double minRange;
        double maxRange;
        System.out.println("Enter the min price range: ");
        minRange = Double.parseDouble(readInputRequireType("double"));
        System.out.println("Enter the max price range: ");
        maxRange = Double.parseDouble(readInputRequireType("double"));
        //swap if the user makes a mistake
        minRange = Math.min(minRange, maxRange);
        maxRange = Math.max(minRange, maxRange);
        boolean anyFound = false;

        for (var car : dealership.getAllVehicles()) {
            if (car.getPrice() >= minRange && car.getPrice() <= maxRange) {
                printFormatted(car);
                anyFound = true;
            }
        }
        if (!anyFound) {
            System.out.println("No cars matched that price range!");
        }
    }

    private static void printFormatted(Vehicle car) {
        NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);

        System.out.printf("%-8d %-6d %-15s %-18s %-12s %-12s %13s %14s%n",
                car.getVin(),
                car.getYear(),
                car.getMake(),
                car.getModel(),
                car.getVehicleType(),
                car.getColor(),
                car.getOdometer(),
                currency.format(car.getPrice()));
    }

    private static void printMenu() {
        System.out.println("""
                Welcome to the Dealership! What would you like to do?
                1 - Find vehicles within a price range
                2 - Find vehicles by make / model
                3 - Find vehicles by year range
                4 - Find vehicles by color
                5 - Find vehicles by mileage range
                6 - Find vehicles by type (car, truck, SUV, van)
                7 - List ALL vehicles
                8 - Add a vehicle
                9 - Remove a vehicle
                99 - Quit
                
                Type in a number corresponding to the action you'd like to do:
                """);
    }

    public static void init() {
        dealership = DealershipFileManager.getDealership();
    }

    //created this method to prevent all parsing errors, exceptions, etc for any type of data input from user.
    //This method only takes three strings as valid argument for expectedType: "int", "double and "string"
    //for char type I recommend using "string" then using charAt()
    // like char option = readInputRequireType("string").charAt(0)
    private static String readInputRequireType(String expectedType) {
        final Pattern alnum = Pattern.compile("^[a-z0-9]+$");
        final Pattern intPattern = Pattern.compile("^\\d+$");
        final Pattern doublePattern = Pattern.compile("^\\d+(\\.\\d+)?$"); // like 23 or 23.45

        String dataTypeName = expectedType == null ? "string" : expectedType.trim().toLowerCase(Locale.ROOT);

        while (true) {
            try {
                if (!scanner.hasNextLine()) {
                    System.out.println("No input detected. Please try again.");
                    continue;
                }

                String s = scanner.nextLine();
                s = s.trim().toLowerCase(Locale.ROOT);

                if (s.isEmpty()) {
                    System.out.println("Input was empty. Please try again.");
                    continue;
                }

                switch (dataTypeName) {
                    case "int": {
                        if (!intPattern.matcher(s).matches()) {
                            System.out.println("Only digits 0–9 are allowed for an integer. Please try again.");
                            continue;
                        }
                        // normalize leading zeros and I only keep one zero if the number is zero
                        s = s.replaceFirst("^0+(?!$)", "");
                        return s; // parse-safe for Integer.parseInt format-wise
                    }
                    case "double": {
                        if (!doublePattern.matcher(s).matches()) {
                            System.out.println("Enter a decimal like 23 or 23.45 (digits with at most one dot). Please try again.");
                            continue;
                        }
                        // normalize leading zeros in integer part
                        int dot = s.indexOf('.');
                        if (dot >= 0) {
                            String intPart = s.substring(0, dot).replaceFirst("^0+(?!$)", "");
                            String fracPart = s.substring(dot + 1); // already digits by regex
                            s = intPart + "." + fracPart;
                        } else {
                            s = s.replaceFirst("^0+(?!$)", "");
                        }
                        return s; // parse-safe for Double.parseDouble
                    }
                    case "string":
                    default: {
                        // keep letters and digits only
                        s = s.replaceAll("[^a-z0-9]", "");
                        if (s.isEmpty() || !alnum.matcher(s).matches()) {
                            System.out.println("Only letters and numbers are allowed. Please try again.");
                            continue;
                        }
                        return s;
                    }
                }

            } catch (NoSuchElementException e) {
                System.out.println("No input available. Please try again.");
            } catch (IllegalStateException e) {
                System.out.println("Input stream is not available. Please try again.");
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }

}
