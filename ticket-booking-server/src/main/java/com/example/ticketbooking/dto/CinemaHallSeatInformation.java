package com.example.ticketbooking.dto;

import com.example.ticketbooking.domain.SeatType;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
@Builder
public class CinemaHallSeatInformation {

    @NotNull
    UUID id;

    Integer seatRow;

    Integer seatColumn;

    SeatType seatType;

    @NotNull
    CinemaHallInformation cinemaHallInformation;
}
