package com.example.travelAgency.HotelRoom;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomMapper implements RowMapper<Room>{

    public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
        Room room = new Room();
        room.setId(rs.getInt("id_pokoju"));
        room.setRoomNumber(rs.getString("numer_pokoju"));
        room.setPeopleNumber(rs.getInt("ilosc_osob"));
        room.setHotelId(rs.getInt("id_hotelu"));
        room.setPrice(rs.getFloat("cena_za_dobe"));
        return room;
    }
}
