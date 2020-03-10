package com.example.ontime;

public class CurrentRequests {
    private String name;
    private String phone;
    private String email;
    private String srclocation;
    private String destination;
    private String amount;

    CurrentRequests(){}
    CurrentRequests(String name, String phone, String email, String srclocation, String destination, String amount ) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.srclocation = srclocation;
        this.destination = destination;
        this.amount = amount;
    }

    String getName() {
        return this.name;
    }
    String getPhone() {
        return this.phone;
    }
    String getEmail() {
        return this.email;
    }
    String getSrclocation() {
        return this.srclocation;
    }
    String getDestination() {
        return this.destination;
    }
    String getAmount() {
        return this.amount;
    }
}
