package com.example.ticketbooking.domain;

import com.example.ticketbooking.dto.CityInformation;
import com.example.ticketbooking.dto.CreateCityInfoRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class City extends AbstractBaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String state;

    private Integer zipCode;

    public static City from(final CreateCityInfoRequest cityInfoRequest) {
        return City.builder().name(cityInfoRequest.getName()).state(cityInfoRequest.getState())
                .zipCode(cityInfoRequest.getZipCode()).build();
    }

    public static CityInformation to(final City city) {
        return CityInformation.builder().id(city.getId()).name(city.getName()).state(city.getState()).zipCode(city.getZipCode())
                .build();
    }
}
