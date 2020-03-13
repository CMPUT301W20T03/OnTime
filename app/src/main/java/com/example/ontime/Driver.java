package com.example.ontime;


import android.media.Image;


/**
 * This is a class that implements Driver object
 */
public class Driver {
    private String DriverName;
    private String Password;
    private String Email;
    private String PhoneNumber;
    private Image photo;

    /**
     * Sole constructor
     */
    public Driver(String DriverName, String password, String email, String phoneNumber){
        this.DriverName = DriverName;
        this.Password = password;
        this.Email = email;
        this.PhoneNumber = phoneNumber;

    }

    /**
     * This returns the name of the driver
     * @return
     *      Return the name of the driver
     */
    public String getUsername(){
        return this.DriverName;
    }

    /**
     * This returns the password of the driver
     * @return
     *      Return the password of the driver
     */
    public String getPassword() {
        return Password;
    }

    /**
     * This returns the email of the driver
     * @return
     *      Return the email of the driver
     */
    public String getEmail(){
        return this.Email;
    }

    /**
     * This returns the PhoneNumber of the driver
     * @return
     *      Return the PhoneNumber of the driver
     */
    public String getPhoneNumber(){
        return this.PhoneNumber;
    }

    /**
     * This returns the Photo of the driver
     * @return
     *      Return the Photo of the driver
     */
    public Image getPhoto(){
        return this.photo;
    }


    /**
     * This sets the Username of the Driver
     * @param username
     *      sets the Username of the Driver
     */
    public void setUsername(String username){
        this.DriverName = username;
    }

    /**
     * This sets the password of the driver
     * @param password
     *      sets the password of the driver
     */
    public void setPassword(String password){
        this.Password = password;
    }

    /**
     * This sets the email of the driver
     * @param email
     *      sets the email of the driver
     */
    public void setEmail(String email){
        this.Email = email;
    }

    /**
     * This sets the phoneNumber of the driver
     * @param phoneNumber
     *      sets the phoneNumber of the driver
     */
    public void setPhoneNumber(String phoneNumber){
        this.PhoneNumber = phoneNumber;
    }

    /**
     * This sets the photo of the driver, still to be implemented
     * @param photo
     *      sets the photo of the driver
     */
   public void uploadPhoto(Image photo){

   }
}
