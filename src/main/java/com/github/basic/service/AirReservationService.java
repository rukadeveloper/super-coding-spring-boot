package com.github.basic.service;

import com.github.basic.repository.airlineTicket.AirlineTicket;
import com.github.basic.repository.airlineTicket.AirlineTicketAndFlightInfo;
import com.github.basic.repository.airlineTicket.AirlineTicketRepository;
import com.github.basic.repository.passenger.Passenger;
import com.github.basic.repository.passenger.PassengerRepository;
import com.github.basic.repository.reservation.Reservation;
import com.github.basic.repository.reservation.ReservationRepository;
import com.github.basic.repository.users.UserEntity;
import com.github.basic.repository.users.UserRepository;
import com.github.basic.web.dto.airline.ReservationRequest;
import com.github.basic.web.dto.airline.ReservationResult;
import com.github.basic.web.dto.airline.Ticket;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AirReservationService {

    private UserRepository userRepository;
    private AirlineTicketRepository airlineTicketRepository;
    private PassengerRepository passengerRepository;
    private ReservationRepository reservationRepository;

    public List<Ticket> findUserFavoritePlaceTickets(Integer userId, String ticketType) {

        // 필요한 레포지토리 : UserRepository, AirlineTicketRepository
        // User 정보 중 userId 가져와서, 선호하는 여행지 도출
        UserEntity userEntity = userRepository.findUserById(userId);
        String likePlace = userEntity.getLikeTravelPlace();
        // 선호하는 여행지와 ticketType으로 airlineTicket 질의하여 AirlineTicket 들고오기
        List<AirlineTicket> airlineTickets = airlineTicketRepository.findAllAirlineTicketWithPlaceAndType(likePlace, ticketType);
        // 둘의 정보를 조합해 TicketDTO 만들기
        List<Ticket> tickets = airlineTickets.stream().map(Ticket::new).collect(Collectors.toList());
        return tickets;
    }

    @Transactional(transactionManager = "tm2")
    public ReservationResult makeReservation(ReservationRequest reservationRequest) {

        // 필요한 레포지토리 : ReservationRepository, Join(Flight / airlineTicket), PassengerRepository

        // 0. userId, airline_ticket_id
        Integer userId = reservationRequest.getUserId();
        Integer airlineTicketId = reservationRequest.getAirlineTicketId();

        // 1. PassengerId
        Passenger passenger = passengerRepository.findPassengerByUserId(userId);
        Integer passengerId = passenger.getPassengerId();

        // 2. 정보 불러오기
        List<AirlineTicketAndFlightInfo> airlineTicketAndFlightInfo
                = airlineTicketRepository.findAllAirlineTicketAndFlightInfo(airlineTicketId);

        // 3. reservation 생성
        Reservation reservation = new Reservation(passengerId, airlineTicketId);
        Boolean isSuccess = reservationRepository.saveRservation(reservation);

        // 4. TODO: ReservationResult DTO
        List<Double> prices = airlineTicketAndFlightInfo.stream().map(AirlineTicketAndFlightInfo::getPrice)
                .collect(Collectors.toList());
        List<Double> charges = airlineTicketAndFlightInfo.stream().map(AirlineTicketAndFlightInfo::getCharge)
                .collect(Collectors.toList());
        Double tax = airlineTicketAndFlightInfo.stream().map(AirlineTicketAndFlightInfo::getTax).findFirst().get();
        Double totalPrice = airlineTicketAndFlightInfo.stream().map(AirlineTicketAndFlightInfo::getTotalPrice).findFirst().get();

        return new ReservationResult(prices,charges,tax,totalPrice,isSuccess);
    }
}
