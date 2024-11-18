package com.github.basic.repository.passenger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {

    private Integer passengerId;
    private Integer userId;
    private String passportNum;
}
