package com.example.ticketbooking.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCinemaHallInfoRequest {
    @NotBlank
    String name;

    @Valid
    @NotNull
    CinemaInformation cinemaInformation;
}
