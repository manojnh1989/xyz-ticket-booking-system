package com.example.ticketbooking.domain;

import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Getter
@MappedSuperclass
public abstract class AbstractBaseEntity {

    @Id
    @Column(
            name = "Id",
            unique = true,
            nullable = false
    )
    @GeneratedValue(
            generator = "system-uuid"
    )
    @GenericGenerator(
            name = "system-uuid",
            strategy = "uuid2"
    )
    @Type(
            type = "uuid-char"
    )
    private UUID id;
}
