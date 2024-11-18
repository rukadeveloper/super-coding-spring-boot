package com.github.basic.repository.reservation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;

@Repository
public class ReservationJdbcTemplateDao implements ReservationRepository {
    private JdbcTemplate jdbcTemplate;

    public ReservationJdbcTemplateDao(@Qualifier("jdbcTemplate2") JdbcTemplate jdbcTemplate ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Boolean saveRservation(Reservation reservation) {
        Integer rowNums = jdbcTemplate.update("INSERT INTO reservation" +
                                              "(passenger_id,airline_ticket_id,reservation_status,reserve_at) "+
                                              "VALUES (?,?,?,?)",
                                              reservation.getPassengerId(), reservation.getAirlineTicketId(),
                                              reservation.getReservationStatus(),
                                              new Date(Timestamp.valueOf(reservation.getReservateAt()).getTime()));
        return rowNums > 0;
    }
}
