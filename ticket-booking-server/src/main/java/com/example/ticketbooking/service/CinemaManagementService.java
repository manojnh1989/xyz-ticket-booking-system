package com.example.ticketbooking.service;

import com.example.ticketbooking.domain.Cinema;
import com.example.ticketbooking.dto.CinemaInformation;
import com.example.ticketbooking.dto.CreateCinemaInfoRequest;
import com.example.ticketbooking.exception.TicketBookingServerException;
import com.example.ticketbooking.exception.error.Error;
import com.example.ticketbooking.repository.CinemaRepository;
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
public class CinemaManagementService {

    private CinemaRepository cinemaRepository;

    private CityManagementService cityManagementService;

    @Transactional
    public List<CinemaInformation> addCinemasInBulk(@Valid @NotEmpty final List<CreateCinemaInfoRequest> createCinemaInfoRequests) {
        final var citiesInfoByName = cityManagementService.findCitiesInfoByName(
                createCinemaInfoRequests.stream().map(info -> info.getCityInformation().getName()).collect(Collectors.toSet())
        );
        return createCinemaInfoRequests.stream().map(info -> {
                    final var cinema = Cinema.from(info);
                    if (!citiesInfoByName.containsKey(info.getCityInformation().getName())) {
                        throw new TicketBookingServerException(new Error().withStatus(HttpStatus.NOT_FOUND));
                    }
                    cinema.setAddress(citiesInfoByName.get(info.getCityInformation().getName()));
                    return Cinema.to(cinemaRepository.save(cinema));
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public CinemaInformation addCinema(@Valid final CreateCinemaInfoRequest cinemaInfoRequest) {
        final var citiesInfoByName = cityManagementService.findCitiesInfoByName(
                Set.of(cinemaInfoRequest.getCityInformation().getName()));
        final var cinema = Cinema.from(cinemaInfoRequest);
        if (!citiesInfoByName.containsKey(cinemaInfoRequest.getCityInformation().getName())) {
            throw new TicketBookingServerException(new Error().withStatus(HttpStatus.NOT_FOUND));
        }
        cinema.setAddress(citiesInfoByName.get(cinemaInfoRequest.getCityInformation().getName()));
        return Cinema.to(cinemaRepository.save(cinema));
    }

    public CinemaInformation findCinemaByIdOrNameAndCity(final Optional<UUID> id, final Optional<String> name,
                                                         final Optional<String> city) {
        if (id.isEmpty() && name.isEmpty()) {
            throw new TicketBookingServerException(
                    new Error("Either of Id or Name shouldn't be null or empty").withStatus(HttpStatus.BAD_REQUEST));
        } else if (name.isPresent() && city.isEmpty()) {
            throw new TicketBookingServerException(
                    new Error("City cannot be empty if cinema name is provided").withStatus(HttpStatus.BAD_REQUEST));
        }

        if (name.isPresent()) {
            return Cinema.to(cinemaRepository.findByNameAndCity(name.get(), city.get()));
        } else {
            return Cinema.to(cinemaRepository.findById(id.get()).orElseThrow(() -> new TicketBookingServerException(
                    new Error("Cinema not found").withStatus(HttpStatus.NOT_FOUND))));
        }
    }

    public Page<CinemaInformation> findCinemasInPages(final Pageable pageable) {
        final var pages = cinemaRepository.findAll(pageable);
        return new PageImpl<>(
                pages.stream().map(Cinema::to).collect(Collectors.toList()), pageable, pages.getTotalElements());
    }

    public Map<UUID, Cinema> findCinemaByIds(final Set<UUID> cinemaIds) {
        return cinemaRepository.findByIdIn(cinemaIds).stream().collect(Collectors.toMap(Cinema::getId, Function.identity()));
    }
}
