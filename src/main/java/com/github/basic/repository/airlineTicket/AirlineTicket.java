package com.github.basic.repository.airlineTicket;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDateTime;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AirlineTicket {

    private Integer ticketId;
    private String ticketType;
    private String departureLoc;
    private String arrivalLoc;
    private LocalDateTime departureAt;
    private LocalDateTime returnAt;
    private Double tax;
    private Double totalPrice;

    public AirlineTicket(Integer ticketId, String ticketType, String departureLoc, String arrivalLoc, Date departureAt, Date returnAt, Double tax, Double totalPrice) {
        this.ticketId = ticketId;
        this.ticketType = ticketType;
        this.departureLoc = departureLoc;
        this.arrivalLoc = arrivalLoc;
        this.departureAt = departureAt.toLocalDate().atStartOfDay();
        this.returnAt = returnAt.toLocalDate().atStartOfDay();
        this.tax = tax;
        this.totalPrice = totalPrice;
    }
}
