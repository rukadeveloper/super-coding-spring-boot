package com.github.basic.repository.airlineTicket;

import java.util.List;

public interface AirlineTicketRepository {
    List<AirlineTicket> findAllAirlineTicketWithPlaceAndType(String likePlace, String ticketType);

    List<AirlineTicketAndFlightInfo> findAllAirlineTicketAndFlightInfo(Integer airlineTicketId);
}
