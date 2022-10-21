package com.example.ticketbooking.controller;

import com.example.ticketbooking.dto.CinemaInformation;
import com.example.ticketbooking.dto.CreateCinemaInfoRequest;
import com.example.ticketbooking.service.CinemaManagementService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("/v1/cinema")
@RestController
@AllArgsConstructor
public class CinemaManagementController {

    private CinemaManagementService cinemaManagementService;

    @PostMapping("/bulk")
    public List<CinemaInformation> addCinemas(final @RequestBody List<CreateCinemaInfoRequest> createCinemaInfoRequests) {
        return cinemaManagementService.addCinemasInBulk(createCinemaInfoRequests);
    }

    @PostMapping
    public CinemaInformation addCinema(final @RequestBody CreateCinemaInfoRequest cinemaInfoRequest) {
        return cinemaManagementService.addCinema(cinemaInfoRequest);
    }

    @GetMapping(value = {
            "/{id}",
            StringUtils.EMPTY
    })
    public CinemaInformation findCinemaByIdOrNameAndCity(@PathVariable(value = "id", required = false) Optional<UUID> id,
                                                         @RequestParam(value = "name", required = false) Optional<String> name,
                                                         @RequestParam(value = "city", required = false) Optional<String> city) {
        return cinemaManagementService.findCinemaByIdOrNameAndCity(id, name, city);
    }

    @PostMapping(value = "/pages")
    public Page<CinemaInformation> findCinemasInPages(final Pageable pageable) {
        return cinemaManagementService.findCinemasInPages(pageable);
    }
}
