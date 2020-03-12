package com.example.ontime;

import java.io.Serializable;

public class CurrentRequests implements Serializable { // Firebase data extract requires data to be public
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

    public void setRider(String rider) {
        this.rider = rider;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSrcLocationText(String srcLocationText) {
        this.srcLocationText = srcLocationText;
    }

    public void setDestinationText(String destinationText) {
        this.destinationText = destinationText;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
