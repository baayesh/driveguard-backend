package com.example.driveguard.Domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FineList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fineListId;
    private String fineName;
    private String fineDescription;
    private Float fineAmount;
    private Integer OffenceChangeValue;
}
