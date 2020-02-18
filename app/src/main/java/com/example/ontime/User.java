package com.example.ontime;

public class User {
    private String username;
    private String pswd;

    User(String username, String pswd){
        this.username = username;
        this.pswd = pswd;
    }

    String getUsername(){
        return this.username;
    }

    String getPswd(){
        return this.pswd;
    }
}
