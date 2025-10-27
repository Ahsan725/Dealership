package com.pluralsight;

import java.util.ArrayList;
import java.util.List;

public class Dealership {
    private String name;
    private String address;
    private String phone;
    private List<Vehicle> inventory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Vehicle> getInventory() {
        return inventory;
    }

    public void setInventory(List<Vehicle> inventory) {
        this.inventory = inventory;
    }

    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<>();
    }

    public void addVehicle(Vehicle v) {
        inventory.add(v);
    }

    public void removeVehicle(Vehicle car){
        inventory.removeIf(vehicle -> car.getVin() == vehicle.getVin());
    }

    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(inventory);
    }

    //TODO:
    public List<Vehicle> getVehiclesByPrice(double min, double max) {
        min = Math.min(min, max);
        max = Math.max(min, max);
        List<Vehicle> result = new ArrayList<>();
        for (var car : inventory) {
            if (car.getPrice() >= min && car.getPrice() <= max) {
                result.add(car);
            }
        }
        return result;
    }

    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        return null;
    }

    public List<Vehicle> getVehiclesByYear(int min, int max) {
        List<Vehicle> result = new ArrayList<>();
        int minRange = Math.min(min, max);
        int maxRange = Math.max(min, max);
        for (var car : inventory) {
            if (car.getYear() >= minRange && car.getYear() <= maxRange) {
                result.add(car);
            }
        }
        return result;
    }

    public List<Vehicle> getVehiclesByColor(String color) {
        List<Vehicle> result = new ArrayList<>();
        for (var car : inventory) {
            if (car.getColor().equalsIgnoreCase(color)){
                result.add(car);
            }
        }
        return result;
    }

    public List<Vehicle> getVehiclesByMileage(int min, int max) {
        return null;
    }

    public List<Vehicle> getVehiclesByType(String type) {
        return null;
    }

    public boolean removeVehicle(int vin) {
        return false;
    }

}
