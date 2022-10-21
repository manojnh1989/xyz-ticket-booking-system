package com.example.ticketbooking.service;

import com.example.ticketbooking.domain.CinemaHallSeat;
import com.example.ticketbooking.dto.CinemaHallSeatInformation;
import com.example.ticketbooking.dto.CreateCinemaHallSeatInfoRequest;
import com.example.ticketbooking.exception.TicketBookingServerException;
import com.example.ticketbooking.exception.error.Error;
import com.example.ticketbooking.repository.CinemaHallSeatRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.ticketbooking.exception.error.ErrorConstants.Messages.NOT_FOUND;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
@Validated
public class CinemaHallSeatManagementService {

    private CinemaHallSeatRepository cinemaHallSeatRepository;
    private CinemaHallManagementService cinemaHallManagementService;

    @Transactional
    public List<CinemaHallSeatInformation> addCinemaHallSeatsInBulk(
            @Valid @NotEmpty final List<CreateCinemaHallSeatInfoRequest> cinemaHallSeatInfoRequests) {
        final var cinemaHallById = cinemaHallManagementService.findCinemaHallByIds(
                cinemaHallSeatInfoRequests.stream().map(CreateCinemaHallSeatInfoRequest::getCinemaHallId)
                        .collect(Collectors.toSet())
        );
        return cinemaHallSeatInfoRequests.stream().map(info -> {
                    final var cinemaHallSeat = CinemaHallSeat.from(info);
                    if (!cinemaHallById.containsKey(info.getCinemaHallId())) {
                        throw new TicketBookingServerException(new Error(String.format(NOT_FOUND, "CinemaHall",
                                info.getCinemaHallId())).withStatus(HttpStatus.NOT_FOUND));
                    }
                    cinemaHallSeat.setCinemaHall(cinemaHallById.get(info.getCinemaHallId()));
                    return CinemaHallSeat.to(cinemaHallSeatRepository.save(cinemaHallSeat));
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public CinemaHallSeatInformation addCinemaHallSeat(
            @Valid @NotEmpty final CreateCinemaHallSeatInfoRequest request) {
        final var cinemaHallById = cinemaHallManagementService.findCinemaHallByIds(
                Set.of(request.getCinemaHallId()));
        final var cinemaHallSeat = CinemaHallSeat.from(request);
        if (!cinemaHallById.containsKey(request.getCinemaHallId())) {
            throw new TicketBookingServerException(new Error(String.format(NOT_FOUND, "CinemaHall",
                    request.getCinemaHallId())).withStatus(HttpStatus.NOT_FOUND));
        }
        cinemaHallSeat.setCinemaHall(cinemaHallById.get(request.getCinemaHallId()));
        return CinemaHallSeat.to(cinemaHallSeatRepository.save(cinemaHallSeat));
    }

    public List<CinemaHallSeatInformation> getAllCinemaHallSeatsByCinemaHall(final @NotNull UUID cinemaHallId) {
        return cinemaHallSeatRepository.findAllByCinemaHallId(cinemaHallId);
    }
}
