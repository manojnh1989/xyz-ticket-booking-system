package com.example.ticketbooking.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder
public class CreateCinemaInfoRequest {
    String name;
    @NotNull
    CityInformation cityInformation;
}
