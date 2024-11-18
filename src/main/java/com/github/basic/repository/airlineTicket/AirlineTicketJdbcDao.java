package com.github.basic.repository.airlineTicket;

import com.github.basic.repository.users.UserEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AirlineTicketJdbcDao implements AirlineTicketRepository {

    private JdbcTemplate jdbcTemplate;

    public AirlineTicketJdbcDao(@Qualifier("jdbcTemplate2") JdbcTemplate jdbcTemplate ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    static RowMapper<AirlineTicket> airlineTicketRowMapper = ((rs, rowNum) ->
            new AirlineTicket(
                    rs.getInt("ticket_id"),
                    rs.getNString("ticket_type"),
                    rs.getNString("departure_loc"),
                    rs.getNString("arrival_loc"),
                    rs.getDate("departure_at"),
                    rs.getDate("return_at"),
                    rs.getDouble("tax"),
                    rs.getDouble("total_price")
            )
    );

    static RowMapper<AirlineTicketAndFlightInfo> airlineTicketAndFlightInfoRowMapper = ((rs, rowNum) ->
            new AirlineTicketAndFlightInfo(
                    rs.getInt("A.ticket_id"),
                    rs.getDouble("F.flight_price"),
                    rs.getDouble("F.charge"),
                    rs.getDouble("A.tax"),
                    rs.getDouble("A.total_price")
            )
    );

    @Override
    public List<AirlineTicket> findAllAirlineTicketWithPlaceAndType(String likePlace, String ticketType) {
        return jdbcTemplate.query("SELECT * FROM airline_ticket WHERE arrival_loc = ? AND ticket_type = ?",
                          airlineTicketRowMapper, likePlace, ticketType);
    }

    @Override
    public List<AirlineTicketAndFlightInfo> findAllAirlineTicketAndFlightInfo(Integer airlineTicketId) {
        return jdbcTemplate.query("SELECT A.ticket_id, A.tax, A.total_price, F.flight_price, F.charge " +
                                  " FROM airline_ticket A INNER JOIN flight F " +
                                  "ON A.ticket_id = F.ticket_id " +
                                  "WHERE A.ticket_id = ?",airlineTicketAndFlightInfoRowMapper, airlineTicketId);
    }
}
