package com.example.ontime;

import android.media.Image;

/**
 * This is a class that implements Rider object
 */
public class Rider {
    private String Ridername;
    private String Password;
    private String Email;
    private String PhoneNumber;
    private Image photo;

    /**
     * Sole constructor
     */
    public Rider(String Ridername, String password, String email, String phoneNumber){
        this.Ridername = Ridername;
        this.Password = password;
        this.Email = email;
        this.PhoneNumber = phoneNumber;

    }

    /**
     * This returns the name of the rider
     * @return
     *      Return the name of the rider
     */
    public String getUsername(){
        return this.Ridername;
    }

    /**
     * This returns the password of the rider
     * @return
     *      Return the password of the rider
     */
    public String getPassword() {
        return Password;
    }

    /**
     * This returns the email of the rider
     * @return
     *      Return the email of the rider
     */
    public String getEmail(){
        return this.Email;
    }

    /**
     * This returns the PhoneNumber of the rider
     * @return
     *      Return the PhoneNumber of the rider
     */
    public String getPhoneNumber(){
        return this.PhoneNumber;
    }

    /**
     * This returns the Photo of the rider
     * @return
     *      Return the Photo of the rider
     */
    public Image getPhoto(){
        return this.photo;
    }

    /**
     * This sets the Username of the rider
     * @param username
     *      sets the Username of the rider
     */
    public void setUsername(String username){
        this.Ridername = Ridername;
    }

    /**
     * This sets the password of the rider
     * @param password
     *      sets the password of the rider
     */
    public void setPassword(String password){
        this.Password = password;
    }

    /**
     * This sets the email of the rider
     * @param email
     *      sets the email of the rider
     */
    public void setEmail(String email){
        this.Email = email;
    }

    /**
     * This sets the phoneNumber of the rider
     * @param phoneNumber
     *      sets the phoneNumber of the rider
     */
    public void setPhoneNumber(String phoneNumber){
        this.PhoneNumber = phoneNumber;
    }

    /**
     * This sets the photo of the rider, still to be implemented
     * @param photo
     *      sets the photo of the rider
     */
    public void uploadPhoto(Image photo){

    }
}
