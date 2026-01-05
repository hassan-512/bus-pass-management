package com.example.buspasssystem.models;


public class Route {
    public int id;
    public String from;
    public String to;
    public int fare;
    public int seats;


    public Route(int id, String from, String to, int fare, int seats){
        this.id=id;
        this.from=from;
        this.to=to;
        this.fare=fare;
        this.seats=seats;
    }
}