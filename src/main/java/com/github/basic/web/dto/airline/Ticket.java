package com.github.basic.web.dto.airline;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.basic.repository.airlineTicket.AirlineTicket;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Ticket {

    private String depart;
    private String arrival;
    private String departureTime;
    private String returnTime;
    private Integer ticketId;
    private AirlineTicket airlineTicket;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Ticket(AirlineTicket airlineTicket) {
        this.depart = airlineTicket.getDepartureLoc();
        this.arrival = airlineTicket.getArrivalLoc();
        this.departureTime = airlineTicket.getDepartureAt().format(formatter);
        this.returnTime = airlineTicket.getReturnAt().format(formatter);
        this.ticketId = airlineTicket.getTicketId();
    }

}
