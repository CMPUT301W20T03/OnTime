package com.example.ontime;

public class Requests {
    private String name;
    private String phone;
    private String email;
    private String srclocation;
    private String destination;
    private String amount;

    Requests(String name, String phone, String email, String srclocation, String destination, String amount ) {
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
    String getTime() {
        return this.phone;
    }
    String getSystolic() {
        return this.email;
    }
    String getDiastolic() {
        return this.srclocation;
    }
    String getRate() {
        return this.destination;
    }
    String getComment() {
        return this.amount;
    }
}
