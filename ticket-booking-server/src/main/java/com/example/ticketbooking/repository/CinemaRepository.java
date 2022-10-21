package com.example.ticketbooking.repository;

import com.example.ticketbooking.domain.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, UUID> {

    List<Cinema> findByIdIn(Collection<UUID> ids);

    @Query("SELECT c FROM Cinema c where c.name = :name AND c.address.name = :city")
    Cinema findByNameAndCity(final @Param("name") String name, final @Param("city") String city);
}
