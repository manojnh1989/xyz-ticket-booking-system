package com.example.ticketbooking.repository;

import com.example.ticketbooking.domain.Cinema;
import com.example.ticketbooking.domain.CinemaHallSeat;
import com.example.ticketbooking.dto.CinemaHallSeatInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface CinemaHallSeatRepository extends JpaRepository<CinemaHallSeat, UUID> {

    List<Cinema> findByIdIn(Collection<UUID> ids);

    @Query("SELECT chs FROM CinemaHallSeat chs WHERE chs.cinemaHall.id = :id")
    List<CinemaHallSeatInformation> findAllByCinemaHallId(final @Param("id") UUID cinemaHallId);
}
