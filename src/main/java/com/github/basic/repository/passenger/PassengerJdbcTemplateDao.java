package com.github.basic.repository.passenger;

import com.github.basic.repository.airlineTicket.AirlineTicket;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PassengerJdbcTemplateDao implements PassengerRepository {

    private JdbcTemplate jdbcTemplate;

    public PassengerJdbcTemplateDao(@Qualifier("jdbcTemplate2") JdbcTemplate jdbcTemplate ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    static RowMapper<Passenger> passengerRowMapper = ((rs, rowNum) ->
            new Passenger(
                    rs.getInt("passenger_id"),
                    rs.getInt("user_id"),
                    rs.getNString("passport_num")
            )
    );

    @Override
    public Passenger findPassengerByUserId(Integer userId) {
        return jdbcTemplate.queryForObject("SELECT * FROM passenger WHERE user_id = ?", passengerRowMapper, userId);
    }
}
