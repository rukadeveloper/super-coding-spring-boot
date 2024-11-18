package com.github.basic.web.controller;

import com.github.basic.service.AirReservationService;
import com.github.basic.web.dto.airline.ReservationRequest;
import com.github.basic.web.dto.airline.ReservationResult;
import com.github.basic.web.dto.airline.Ticket;
import com.github.basic.web.dto.airline.TicketResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/air-reservation")
@AllArgsConstructor
public class AirReservationController {

    private AirReservationService airReservationService;

    @GetMapping("/tickets")
    public TicketResponse findAirlineTickets(@RequestParam("user-id")Integer userId,
                                             @RequestParam("airline-ticket-type") String ticketType) {

        List<Ticket> tickets = airReservationService.findUserFavoritePlaceTickets(userId,ticketType);
        return new TicketResponse(tickets);
    }

    @PostMapping("/reservation")
    public ReservationResult makeReservation(@RequestBody ReservationRequest reservationRequest) {

        return airReservationService.makeReservation(reservationRequest);
    }
}
