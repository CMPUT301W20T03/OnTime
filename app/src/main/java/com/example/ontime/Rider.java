package com.example.ontime;

import android.media.Image;

public class Rider {
    private String Ridername;
    private String Password;
    private String Email;
    private String PhoneNumber;
    private Image photo;

    public Rider(String Ridername, String password, String email, String phoneNumber){
        this.Ridername = Ridername;
        this.Password = password;
        this.Email = email;
        this.PhoneNumber = phoneNumber;

    }

    /*
     *this part is getter
     */

    public String getUsername(){
        return this.Ridername;
    }

    public String getPassword() {
        return Password;
    }

    public String getEmail(){
        return this.Email;
    }

    public String getPhoneNumber(){
        return this.PhoneNumber;
    }

    public Image getPhoto(){
        return this.photo;
    }

    /*
     * setter
     * */

    public void setUsername(String username){
        this.Ridername = Ridername;
    }

    public void setPassword(String password){
        this.Password = password;
    }

    public void setEmail(String email){
        this.Email = email;
    }

    public void setPhoneNumber(String phoneNumber){
        this.PhoneNumber = phoneNumber;
    }

    //function maybe
    public void uploadPhoto(Image photo){

    }
}
