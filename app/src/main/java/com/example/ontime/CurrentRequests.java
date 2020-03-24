package com.example.ontime;

import java.io.Serializable;

/**
 * This is a class that keeps track of all current active requests
 */
public class CurrentRequests implements Serializable { // Firebase data extract requires data to be public
    public String rider;
    public String phoneNumber;
    public String email;
    public String srcLocationText;
    public String destinationText;
    public String srcCoordinate;
    public String dstCoordinate;
    public String amount;
    public String status;

    /**
     * Sole constructor.
     */
    CurrentRequests() {

    }

    /**
     * Sole constructor.
     */
    CurrentRequests(String name, String phone, String email, String srcLocationText, String destinationText, String amount, String status) {
        this.rider = name;
        this.phoneNumber = phone;
        this.email = email;
        this.srcLocationText = srcLocationText;
        this.destinationText = destinationText;
        this.amount = amount;
        this.status = status;
    }

    /**
     * This returns the name of the rider
     * @return
     *      Return the name of the rider
     */
    String getName() {
        return this.rider;
    }

    /**
     * This returns the phone number of the rider
     * @return
     *      Return the phone number of the rider
     */
    String getPhone() {
        return this.phoneNumber;
    }

    /**
     * This returns the email of the rider
     * @return
     *      Return the email of the rider
     */
    String getEmail() {
        return this.email;
    }

    /**
     * This returns the source location of the rider
     * @return
     *      Return the source location of the rider
     */
    String getSrcLocation() {
        return this.srcLocationText;
    }

    /**
     * This returns the destination of the rider
     * @return
     *      Return the destination of the rider
     */
    String getDestination() {
        return this.destinationText;
    }

    /**
     * This returns the coordinate of source location of the rider
     * @return
     *      Return the coordinate of source location of the rider
     */
    String getSrcCoordinate() {
        return this.srcCoordinate;
    }

    /**
     * This returns the coordinate of destination of the rider
     * @return
     *      Return the coordinate of destination of the rider
     */
    String getDstCoordinate() {
        return this.dstCoordinate;
    }
    /**
     * This returns the amount of the trip
     * @return
     *      Return the amount of the trip
     */
    String getAmount() {
        return this.amount;
    }

    /**
     * This returns the status of the trip
     * @return
     *      Return the status of the trip
     */
    String getStatus() {
        return this.status;
    }

    /**
     * This sets the rider of the trip
     * @param rider
     *      sets the rider of the trip
     */
    public void setRider(String rider) {
        this.rider = rider;
    }

    /**
     * This sets the phone number of the rider of the trip
     * @param phone
     *      sets the phone number of the rider of the trip
     */
    public void setPhone(String phone) {
        this.phoneNumber = phone;
    }

    /**
     * This sets the email of the rider of the trip
     * @param email
     *      sets the email of the rider of the trip
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This sets the source location of the trip
     * @param srcLocationText
     *      sets the source location of the trip
     */
    public void setSrcLocationText(String srcLocationText) {
        this.srcLocationText = srcLocationText;
    }

    /**
     * This sets the destination of the trip
     * @param destinationText
     *      sets the destination of the trip
     */
    public void setDestinationText(String destinationText) {
        this.destinationText = destinationText;
    }

    /**
     * This sets the coordinate of source location of the trip
     * @param coord
     *      sets the coordinate of source location  of the trip
     */
    public void setSrcCoordinate(String coord) {
        this.srcCoordinate = coord;
    }

    /**
     * This sets the coordinate of destination of the trip
     * @param coord
     *      sets the coordinate of destination of the trip
     */
    public void setDstCoordinate(String coord) {
        this.dstCoordinate = coord;
    }
    /**
     * This sets the amount of the trip
     * @param amount
     *      sets the amount of the trip
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * This sets the status of the trip
     * @param status
     *      sets the status of the trip
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
