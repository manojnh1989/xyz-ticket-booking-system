package com.example.ticketbooking.domain;

import com.example.ticketbooking.dto.CinemaInformation;
import com.example.ticketbooking.dto.CreateCinemaInfoRequest;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.CascadeType;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Cinema extends AbstractBaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    private Integer totalCinemaHalls;

    @Setter
    @ManyToOne(targetEntity = City.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "CityId")
    private City address;

    public static Cinema from(final CreateCinemaInfoRequest createCinemaInfoRequest) {
        return Cinema.builder().name(createCinemaInfoRequest.getName()).build();
    }

    public static CinemaInformation to(final Cinema cinema) {
        return CinemaInformation.builder().id(cinema.getId()).name(cinema.getName()).totalCinemaHalls(cinema.getTotalCinemaHalls())
                .cityInformation(City.to(cinema.getAddress())).build();
    }
}
