package com.example.travelAgency.Payment;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentMapper implements RowMapper<Payment> {
    public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Payment payment = new Payment();
        payment.setId(rs.getInt("id_zaplaty"));
        payment.setReservationId(rs.getInt("id_rez"));
        payment.setAmount(rs.getFloat("kwota"));
        payment.setDate(rs.getDate("data"));
        payment.setPaymentType(rs.getString("sposob_zaplaty"));
        return payment;
    }
}
