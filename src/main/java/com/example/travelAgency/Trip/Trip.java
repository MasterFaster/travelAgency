package com.example.travelAgency.Trip;

import com.example.travelAgency.Hotel.Hotel;

import java.util.List;

public class Trip {

    private int id;
    private String destination;
    private float price;        //price for single person
    private float countedPrice;     //price for many people
    private int peopleNumber;
    private List<Hotel> hotels;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<Hotel> getHotels(){
        return hotels;
    }

    public void setHotels(List<Hotel> hotels){
        this.hotels = hotels;
    }

    public int getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(int peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public float getCountedPrice() {
        return countedPrice;
    }

    public void setCountedPrice(float countedPrice) {
        this.countedPrice = countedPrice;
    }
}
