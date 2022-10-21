package com.example.ticketbooking.service;

import com.example.ticketbooking.domain.City;
import com.example.ticketbooking.dto.CityInformation;
import com.example.ticketbooking.dto.CreateCityInfoRequest;
import com.example.ticketbooking.exception.TicketBookingServerException;
import com.example.ticketbooking.exception.error.Error;
import com.example.ticketbooking.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CityManagementService {

    private CityRepository cityRepository;

    @Transactional
    public List<CityInformation> addCitiesInBulk(final List<CreateCityInfoRequest> cityInfoRequests) {
        return cityInfoRequests.stream().map(info -> City.to(cityRepository.save(City.from(info))))
                .collect(Collectors.toList());
    }

    @Transactional
    public CityInformation addCity(final CreateCityInfoRequest cityInfoRequest) {
        return City.to(cityRepository.save(City.from(cityInfoRequest)));
    }

    public CityInformation findCityByIdOrName(final Optional<UUID> id, final Optional<String> name) {
        if (id.isEmpty() && name.isEmpty()) {
            throw new TicketBookingServerException(
                    new Error("Either of Id or Name shouldn't be null or empty").withStatus(HttpStatus.BAD_REQUEST));
        }
        if (name.isPresent()) {
            return City.to(cityRepository.findByName(name.get()));
        } else {
            return City.to(cityRepository.findById(id.get()).orElseThrow(() -> new TicketBookingServerException(
                    new Error("City not found").withStatus(HttpStatus.NOT_FOUND))));
        }
    }

    public Page<CityInformation> findCitiesInPages(final Pageable pageable) {
        final var pages = cityRepository.findAll(pageable);
        return new PageImpl<>(
                pages.stream().map(City::to).collect(Collectors.toList()), pageable, pages.getTotalElements());
    }

    public Map<String, City> findCitiesInfoByName(final Set<String> cityNames) {
        return cityRepository.findByNameIn(cityNames).stream().collect(Collectors.toMap(City::getName, Function.identity()));
    }
}
