package com.hotel.model;

public class FoodService extends HotelService {
    private String mealType;

    public FoodService() {}

    @Override
    public String getServiceType() { return "FOOD"; }
    public FoodService(String id, String name, String description, double price,
                       String availability, String mealType) {
        super(id, name, description, price, availability);
        this.mealType = mealType;
    }

    @Override
    public double calculateCost(int quantity) {
        return super.calculateCost(quantity);
    }

    public String getMealType() { return mealType; }
    public void setMealType(String v) { mealType = v; }
}
