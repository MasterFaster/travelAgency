package com.example.travelAgency.Insurance;

import java.util.Date;

public class Insurance {

    private int id;
    private float price;
    private Date departureDate;
    private Date returnDate;
    private int reservationId;
    private String type;
    private int peopleNumber;

    public Insurance(){}

    public Insurance(float price, Date departureDate, Date returnDate, String type, int peopleNumber){
        this.price = price;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.type = type;
        this.peopleNumber = peopleNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(int peopleNumber) {
        this.peopleNumber = peopleNumber;
    }
}
