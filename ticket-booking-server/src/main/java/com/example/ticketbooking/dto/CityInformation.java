package com.example.ticketbooking.dto;

import lombok.Builder;
import lombok.Value;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
@Builder
public class CityInformation {
    @NotNull
    UUID id;
    String name;
    String state;
    Integer zipCode;
}
