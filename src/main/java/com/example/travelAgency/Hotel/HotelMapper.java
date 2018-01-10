package com.example.travelAgency.Hotel;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HotelMapper implements RowMapper<Hotel> {

    public Hotel mapRow(ResultSet rs, int rowNum) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setId(rs.getInt("id_hotelu"));
        hotel.setName(rs.getString("nazwa"));
        hotel.setStars(rs.getInt("gwiazdki"));
        hotel.setCountry(rs.getString("KRAJ"));
        hotel.setCity(rs.getString("MIASTO"));
        hotel.setStreet(rs.getString("ULICA"));
        hotel.setHouseNumber(rs.getString("NUMER_DOMU"));
        return hotel;
    }
}
