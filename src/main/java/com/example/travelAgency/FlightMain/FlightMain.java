package com.example.travelAgency.FlightMain;

import com.example.travelAgency.Airport.Airport;
import com.example.travelAgency.Flight.Flight;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * FlightMain (Przelot)- contains many flights
 */
public class FlightMain {

    private int id;
    private int reservationId;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private Date departureDate;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private Date arrivalDate;
    private int departureAirportId;
    private int arrivalAirportId;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private float price;
    private int peopleNumber;
    private List<Flight> flights;

    public FlightMain(){
        this.flights = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public int getDepartureAirportId() {
        return departureAirportId;
    }

    public void setDepartureAirportId(int departureAirportId) {
        this.departureAirportId = departureAirportId;
    }

    public int getArrivalAirportId() {
        return arrivalAirportId;
    }

    public void setArrivalAirportId(int arrivalAirportId) {
        this.arrivalAirportId = arrivalAirportId;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(int peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public void calculateDates(){
        if(flights.size() == 1) {
            this.setDepartureDate(flights.get(0).getDepartureDate());
            this.setArrivalDate(flights.get(0).getArrivalDate());
        }else if(flights.size() == 2){
            this.setDepartureDate(flights.get(0).getDepartureDate());
            this.setArrivalDate(flights.get(1).getArrivalDate());
        }
    }
}
