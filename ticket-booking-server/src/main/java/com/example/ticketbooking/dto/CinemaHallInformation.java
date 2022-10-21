package com.example.ticketbooking.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
@Builder
public class CinemaHallInformation {
    @NotNull
    UUID id;
    String name;
    Integer totalSeats;
    @NotNull
    CinemaInformation cinemaInformation;
}
