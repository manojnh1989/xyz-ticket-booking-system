package com.example.ticketbooking.repository;

import com.example.ticketbooking.domain.Cinema;
import com.example.ticketbooking.domain.CinemaHall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface CinemaHallRepository extends JpaRepository<CinemaHall, UUID> {

    List<CinemaHall> findByIdIn(Collection<UUID> ids);

    @Query("SELECT ch FROM CinemaHall ch where ch.name = :name AND ch.cinema.id = :cinemaId")
    CinemaHall findByNameAndCinemaId(final @Param("name") String name, final @Param("cinemaId") UUID id);
}
