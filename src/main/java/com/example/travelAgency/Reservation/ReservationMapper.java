package com.example.travelAgency.Reservation;

import com.example.travelAgency.Client.Client;
import com.example.travelAgency.Hotel.Hotel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationMapper implements RowMapper<Reservation> {

    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(rs.getInt("id_rez"));
        reservation.setDepartureDate(rs.getDate("od"));
        reservation.setReturnDate(rs.getDate("do"));
        reservation.setHotelId(rs.getInt("id_hotelu"));
        reservation.setClientId(rs.getInt("id_klienta"));
        reservation.setPrice(rs.getFloat("cena_suma"));

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
        reservation.setClient(client);


        Hotel hotel = new Hotel();
        hotel.setId(rs.getInt("id_hotelu"));
        hotel.setName(rs.getString("nazwa"));
        hotel.setStars(rs.getInt("gwiazdki"));
        hotel.setCountry(rs.getString("KRAJ"));
        hotel.setCity(rs.getString("MIASTO"));
        hotel.setStreet(rs.getString("ULICA"));
        hotel.setHouseNumber(rs.getString("NUMER_DOMU"));
        reservation.setHotel(hotel);
        return reservation;
    }
}
