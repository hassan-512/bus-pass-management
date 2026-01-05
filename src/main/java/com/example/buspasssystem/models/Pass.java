package com.example.buspasssystem.models;


public class Pass {
    public int id;
    public int userId;
    public String route;
    public int fare;


    public Pass(int id,int userId,String route,int fare){
        this.id=id;
        this.userId=userId;
        this.route=route;
        this.fare=fare;
    }
}