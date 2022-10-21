package com.example.ticketbooking.controller;

import com.example.ticketbooking.dto.CinemaHallInformation;
import com.example.ticketbooking.dto.CreateCinemaHallInfoRequest;
import com.example.ticketbooking.service.CinemaHallManagementService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("/v1/cinema-hall")
@RestController
@AllArgsConstructor
@Validated
public class CinemaHallManagementController {

    private CinemaHallManagementService cinemaHallManagementService;

    @PostMapping("/bulk")
    public List<CinemaHallInformation> addCinemaHalls(final @RequestBody List<CreateCinemaHallInfoRequest> createCinemaHallInfoRequests) {
        return cinemaHallManagementService.addCinemaHallsInBulk(createCinemaHallInfoRequests);
    }

    @PostMapping
    public CinemaHallInformation addCinemaHall(final @RequestBody CreateCinemaHallInfoRequest createCinemaHallInfoRequest) {
        return cinemaHallManagementService.addCinemaHall(createCinemaHallInfoRequest);
    }

    @GetMapping(value = {
            "/{id}",
            StringUtils.EMPTY
    })
    public CinemaHallInformation findCinemaHallByIdOrNameAndCinemaId(
                                                         @PathVariable(value = "id", required = false) Optional<UUID> id,
                                                         @RequestParam(value = "name", required = false) Optional<String> name,
                                                         @RequestParam(value = "cinemaId", required = false) Optional<UUID> cinemaId) {
        return cinemaHallManagementService.findCinemaHallByIdOrNameAndCinemaId(id, name, cinemaId);
    }

    @PostMapping(value = "/pages")
    public Page<CinemaHallInformation> findCinemaHallsInPages(final Pageable pageable) {
        return cinemaHallManagementService.findCinemaHallsInPages(pageable);
    }
}
