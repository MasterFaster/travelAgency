package com.example.travelAgency.Client;

import org.springframework.stereotype.Component;

@Component
public class Client {

    private String firstName;
    private String secondName;
    private int pesel;
    private int phoneNumber;
    private String email;
    private String country;
    private String city;
    private String street;
    private String houseNumber;
    private int id;


    public Client(){
        //System.out.println("Client created");
    }

    public Client(String firstName, String secondName, int pesel, int phoneNumber, String email, String country, String city,
                  String street, String houseNumber){
        this.firstName = firstName;
        this.secondName = secondName;
        this.pesel = pesel;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public void introduce(){
        System.out.println("Hello im client");
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public String toString() {
        return firstName+ " " +secondName;
    }

    public int getPesel() {
        return pesel;
    }

    public void setPesel(int pesel) {
        this.pesel = pesel;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
