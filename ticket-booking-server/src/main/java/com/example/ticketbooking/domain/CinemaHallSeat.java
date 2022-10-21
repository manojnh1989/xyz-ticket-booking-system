package com.example.ticketbooking.domain;

import com.example.ticketbooking.dto.CinemaHallInformation;
import com.example.ticketbooking.dto.CinemaHallSeatInformation;
import com.example.ticketbooking.dto.CreateCinemaHallSeatInfoRequest;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CinemaHallSeat extends AbstractBaseEntity {

    private Integer seatRow;

    private Integer seatColumn;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @Setter
    @ManyToOne(targetEntity = CinemaHall.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "CinemaHallId")
    private CinemaHall cinemaHall;

    public static CinemaHallSeat from(final CreateCinemaHallSeatInfoRequest request) {
        return CinemaHallSeat.builder().seatRow(request.getSeatRow()).seatColumn(request.getSeatColumn())
                .seatType(request.getSeatType()).build();
    }

    public static CinemaHallSeatInformation to(final CinemaHallSeat cinemaHallSeat) {
        return CinemaHallSeatInformation.builder().id(cinemaHallSeat.getId()).seatRow(cinemaHallSeat.getSeatRow())
                .seatColumn(cinemaHallSeat.getSeatColumn()).cinemaHallInformation(
                        CinemaHall.to(cinemaHallSeat.getCinemaHall())).build();
    }
}
