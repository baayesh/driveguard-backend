package com.example.driveguard.Application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class FineDataDTO {
    private Integer fineId;
    private Integer officerId;
    private Integer supportingOfficerId;
    private LocalDate fineDate;
    private Integer remainingDaysToPay;
    private String fineStatus;
}
