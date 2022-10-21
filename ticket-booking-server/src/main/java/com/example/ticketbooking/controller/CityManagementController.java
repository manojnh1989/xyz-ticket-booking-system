package com.example.ticketbooking.controller;

import com.example.ticketbooking.dto.CityInformation;
import com.example.ticketbooking.dto.CreateCityInfoRequest;
import com.example.ticketbooking.service.CityManagementService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("/v1/city")
@RestController
@AllArgsConstructor
@Validated
public class CityManagementController {

    private CityManagementService cityManagementService;

    @PostMapping("/bulk")
    public List<CityInformation> addCities(final @Valid @NotEmpty @RequestBody List<CreateCityInfoRequest> cityInfoRequests) {
        return cityManagementService.addCitiesInBulk(cityInfoRequests);
    }

    @PostMapping
    public CityInformation addCity(final @Valid @RequestBody CreateCityInfoRequest cityInfoRequest) {
        return cityManagementService.addCity(cityInfoRequest);
    }

    @GetMapping(value = {
            "/{id}",
            StringUtils.EMPTY
    })
    public CityInformation findCity(@PathVariable(value = "id", required = false) Optional<UUID> id,
                                    @RequestParam(value = "name", required = false) Optional<String> name) {
        return cityManagementService.findCityByIdOrName(id, name);
    }

    @PostMapping(value = "/pages")
    public Page<CityInformation> findCitiesInPages(final Pageable pageable) {
        return cityManagementService.findCitiesInPages(pageable);
    }
}
