package com.githiomi.somo.Models;

public class User {

    private String username;
    private String role;

    // Empty constructor
    public User() {
    }

    // Constructor
    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
