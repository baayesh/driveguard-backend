package com.example.driveguard.Domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fine")
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fineId;
    private Integer driverId;
    private Integer fineListId;
    private Date fineDate;
    private String fineStatus;
    private Integer officerId;
    private Integer supportingOfficerId;
    private Integer remainingDaysToPay;
}
