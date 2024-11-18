package com.github.basic.repository.passenger;

public interface PassengerRepository {
    Passenger findPassengerByUserId(Integer userId);
}
