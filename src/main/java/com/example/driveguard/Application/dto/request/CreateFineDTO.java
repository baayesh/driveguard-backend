package com.example.driveguard.Application.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreateFineDTO {
    private Integer driverId;
    private String fineName;
    private Integer fineListId;
    private Date fineDate;
    private Integer officerId;
    private Integer supportingOfficerId;
    private String fineStatus;
    private Integer remainingDaysToPay;
}
