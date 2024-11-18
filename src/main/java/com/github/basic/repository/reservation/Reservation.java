package com.github.basic.repository.reservation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Reservation {

    private Integer reservationId;
    private Integer passengerId;
    private Integer airlineTicketId;
    private String reservationStatus;
    private LocalDateTime reservateAt;

    public Reservation(Integer passengerId, Integer airlineTicketId) {
        this.passengerId = passengerId;
        this.airlineTicketId = airlineTicketId;
        this.reservationStatus = "대기";
        this.reservateAt = LocalDateTime.now();
    }
}
