package com.example.buspasssystem.models;


public class User {
    public int id;
    public String email;
    public String password;
    public int balance;


    public User(int id, String email, String password, int balance){
        this.id=id;
        this.email=email;
        this.password=password;
        this.balance=balance;
    }
}