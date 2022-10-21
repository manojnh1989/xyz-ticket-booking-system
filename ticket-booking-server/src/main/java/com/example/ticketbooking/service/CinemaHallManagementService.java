package com.example.ticketbooking.service;

import com.example.ticketbooking.domain.Cinema;
import com.example.ticketbooking.domain.CinemaHall;
import com.example.ticketbooking.dto.CinemaHallInformation;
import com.example.ticketbooking.dto.CreateCinemaHallInfoRequest;
import com.example.ticketbooking.exception.TicketBookingServerException;
import com.example.ticketbooking.exception.error.Error;
import com.example.ticketbooking.repository.CinemaHallRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
@Validated
public class CinemaHallManagementService {

    private CinemaHallRepository cinemaHallRepository;

    private CinemaManagementService cinemaManagementService;

    @Transactional
    public List<CinemaHallInformation> addCinemaHallsInBulk(@Valid @NotEmpty final List<CreateCinemaHallInfoRequest> createCinemaHallInfoRequests) {
        final var cinemaById = cinemaManagementService.findCinemaByIds(
                createCinemaHallInfoRequests.stream().map(info -> info.getCinemaInformation().getId()).collect(Collectors.toSet())
        );
        return createCinemaHallInfoRequests.stream().map(info -> {
                    final var cinemaHall = CinemaHall.from(info);
                    if (!cinemaById.containsKey(info.getCinemaInformation().getId())) {
                        throw new TicketBookingServerException(new Error().withStatus(HttpStatus.NOT_FOUND));
                    }
                    cinemaHall.setCinema(cinemaById.get(info.getCinemaInformation().getId()));
                    return CinemaHall.to(cinemaHallRepository.save(cinemaHall));
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public CinemaHallInformation addCinemaHall(@Valid final CreateCinemaHallInfoRequest createCinemaHallInfoRequest) {
        final var cinemaById = cinemaManagementService.findCinemaByIds(
                Set.of(createCinemaHallInfoRequest.getCinemaInformation().getId()));
        final var cinemaHall = CinemaHall.from(createCinemaHallInfoRequest);
        if (!cinemaById.containsKey(createCinemaHallInfoRequest.getCinemaInformation().getId())) {
            throw new TicketBookingServerException(new Error().withStatus(HttpStatus.NOT_FOUND));
        }
        cinemaHall.setCinema(cinemaById.get(createCinemaHallInfoRequest.getCinemaInformation().getId()));
        return CinemaHall.to(cinemaHallRepository.save(cinemaHall));
    }

    public CinemaHallInformation findCinemaHallByIdOrNameAndCinemaId(
                                                         final Optional<UUID> id, final Optional<String> name,
                                                         final Optional<UUID> cinemaId) {
        if (id.isEmpty() && name.isEmpty()) {
            throw new TicketBookingServerException(
                    new Error("Either of Id or Name shouldn't be null or empty").withStatus(HttpStatus.BAD_REQUEST));
        } else if (name.isPresent() && cinemaId.isEmpty()) {
            throw new TicketBookingServerException(
                    new Error("Cinema id cannot be null if cinema hall name is provided").withStatus(HttpStatus.BAD_REQUEST));
        }

        if (name.isPresent()) {
            return CinemaHall.to(cinemaHallRepository.findByNameAndCinemaId(name.get(), cinemaId.get()));
        } else {
            return CinemaHall.to(cinemaHallRepository.findById(id.get()).orElseThrow(() -> new TicketBookingServerException(
                    new Error("Cinema hall not found").withStatus(HttpStatus.NOT_FOUND))));
        }
    }

    public Page<CinemaHallInformation> findCinemaHallsInPages(final Pageable pageable) {
        final var pages = cinemaHallRepository.findAll(pageable);
        return new PageImpl<>(
                pages.stream().map(CinemaHall::to).collect(Collectors.toList()), pageable, pages.getTotalElements());
    }

    public Map<UUID, CinemaHall> findCinemaHallByIds(final Set<UUID> cinemaHallIds) {
        return cinemaHallRepository.findByIdIn(cinemaHallIds).stream().collect(Collectors.toMap(CinemaHall::getId, Function.identity()));
    }
}
