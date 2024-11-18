package com.github.basic.repository.airlineTicket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AirlineTicketAndFlightInfo {

    private Integer ticketId;
    private Double price;
    private Double charge;
    private Double tax;
    private Double totalPrice;

}
