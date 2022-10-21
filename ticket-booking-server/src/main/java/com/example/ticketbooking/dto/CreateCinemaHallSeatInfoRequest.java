package com.example.ticketbooking.dto;

import com.example.ticketbooking.domain.SeatType;
import lombok.Value;
import lombok.Builder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
@Builder
public class CreateCinemaHallSeatInfoRequest {
    @Min(0)
    @NotNull
    Integer seatRow;

    @Min(0)
    @NotNull
    Integer seatColumn;

    @NotNull
    SeatType seatType;

    @NotNull
    UUID cinemaHallId;
}
