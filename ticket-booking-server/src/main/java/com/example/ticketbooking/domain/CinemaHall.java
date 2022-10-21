package com.example.ticketbooking.domain;

import com.example.ticketbooking.dto.CinemaHallInformation;
import com.example.ticketbooking.dto.CreateCinemaHallInfoRequest;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CinemaHall extends AbstractBaseEntity {

    @Column(nullable = false)
    private String name;

    private Integer totalSeats;

    @Setter
    @ManyToOne(targetEntity = Cinema.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "CinemaId")
    private Cinema cinema;

    public static CinemaHall from(final CreateCinemaHallInfoRequest createCinemaHallInfoRequest) {
        return CinemaHall.builder().name(createCinemaHallInfoRequest.getName()).build();
    }

    public static CinemaHallInformation to(final CinemaHall cinemaHall) {
        return CinemaHallInformation.builder().id(cinemaHall.getId()).name(cinemaHall.getName()).totalSeats(cinemaHall.getTotalSeats())
                .cinemaInformation(Cinema.to(cinemaHall.getCinema())).build();
    }
}
