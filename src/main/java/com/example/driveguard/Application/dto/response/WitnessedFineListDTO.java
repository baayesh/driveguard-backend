package com.example.driveguard.Application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class WitnessedFineListDTO {
    private Integer fineId;
    private LocalDate fineDate;
    private String fineName;
    private String fineDescription;
    private Float fineAmount;
    private String officerFirstName;
    private String officerLastName;
    private Integer remainingDaysToPay;

}
