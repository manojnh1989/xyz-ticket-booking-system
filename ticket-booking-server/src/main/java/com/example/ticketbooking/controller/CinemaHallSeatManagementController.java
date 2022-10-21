package com.example.ticketbooking.controller;

import com.example.ticketbooking.dto.CinemaHallSeatInformation;
import com.example.ticketbooking.dto.CreateCinemaHallSeatInfoRequest;
import com.example.ticketbooking.service.CinemaHallSeatManagementService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/v1/cinema-hall-seat")
@RestController
@AllArgsConstructor
@Validated
public class CinemaHallSeatManagementController {

    private CinemaHallSeatManagementService cinemaHallSeatManagementService;

    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CinemaHallSeatInformation> addCinemaHalls(final @RequestBody List<CreateCinemaHallSeatInfoRequest> cinemaHallSeatInfoRequests) {
        return cinemaHallSeatManagementService.addCinemaHallSeatsInBulk(cinemaHallSeatInfoRequests);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CinemaHallSeatInformation addCinemaHall(final @RequestBody CreateCinemaHallSeatInfoRequest hallSeatInfoRequest) {
        return cinemaHallSeatManagementService.addCinemaHallSeat(hallSeatInfoRequest);
    }

    @GetMapping
    public List<CinemaHallSeatInformation> findAllCinemaHallSeatsByCinemaHallId(
            final @RequestParam("cinemaHallId") UUID cinemaHallId) {
        return cinemaHallSeatManagementService.getAllCinemaHallSeatsByCinemaHall(cinemaHallId);
    }
}
