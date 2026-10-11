package com.hotel.model;

public class Admin extends User {
    private String adminRole;

    public Admin() {}

    public Admin(String userId, String name, String email, String phone,
                 String password, String adminRole) {
        super(userId, name, email, phone, password);
        this.adminRole = adminRole;
    }

    @Override
    public String getUserType() {
        return "ADMIN";
    }

    public String getAdminRole() { return adminRole; }
    public void setAdminRole(String adminRole) { this.adminRole = adminRole; }
}
