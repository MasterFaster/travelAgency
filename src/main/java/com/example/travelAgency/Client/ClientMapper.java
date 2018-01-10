package com.example.travelAgency.Client;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientMapper implements RowMapper<Client> {
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        Client client = new Client();
        client.setFirstName(rs.getString("IMIE"));
        client.setSecondName(rs.getString("NAZWISKO"));
        client.setPesel(rs.getInt("PESEL"));
        client.setPhoneNumber(rs.getInt("TELEFON"));
        client.setEmail(rs.getString("EMAIL"));
        client.setCountry(rs.getString("KRAJ"));
        client.setCity(rs.getString("MIASTO"));
        client.setStreet(rs.getString("ULICA"));
        client.setHouseNumber(rs.getString("NUMER_DOMU"));
        client.setId(rs.getInt("ID_KLIENTA"));
        //System.out.println(client.getFirstName());
        return client;
    }
}
