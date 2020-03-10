package com.example.ontime;

public class CurrentRequests { // Firebase data extract requires data to be public
    public String rider;
    public String phone;
    public String email;
    public String srcLocationText;
    public String destinationText;
    public String amount;

    CurrentRequests() {

    }

    CurrentRequests(String name, String phone, String email, String srcLocationText, String destinationText, String amount) {
        this.rider = name;
        this.phone = phone;
        this.email = email;
        this.srcLocationText = srcLocationText;
        this.destinationText = destinationText;
        this.amount = amount;
    }

    String getName() {
        return this.rider;
    }
    String getPhone() {
        return this.phone;
    }
    String getEmail() {
        return this.email;
    }
    String getSrcLocation() {
        return this.srcLocationText;
    }
    String getDestination() {
        return this.destinationText;
    }
    String getAmount() {
        return this.amount;
    }
}
