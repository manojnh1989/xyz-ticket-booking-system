package com.example.ticketbooking.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
@Builder
public class CinemaInformation {
    @NotNull
    UUID id;
    String name;
    Integer totalCinemaHalls;
    @NotNull
    CityInformation cityInformation;
}
