package com.example.ticketbooking.repository;

import com.example.ticketbooking.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<City, UUID> {

    City findByName(final String name);

    List<City> findByNameIn(final Collection<String> names);
}
