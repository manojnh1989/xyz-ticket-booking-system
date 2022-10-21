package com.example.ticketbooking.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@Builder
public class CreateCityInfoRequest {
    @NotBlank
    String name;
    @NotBlank
    String state;
    Integer zipCode;
}
