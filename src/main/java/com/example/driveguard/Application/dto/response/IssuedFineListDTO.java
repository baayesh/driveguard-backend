package com.example.driveguard.Application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class IssuedFineListDTO {
    private Integer offenseId;
    private LocalDate offenceDate;
    private String drivingLicenseNumber;
    private String driverFirstName;
    private  String driverLastName;
    private Float offenseAmount;
    private String offenseName;
    private String offenseDescription;
    private String issuedOfficerFirstName;
    private String issuedOfficerLastName;
    private Integer issuedOfficerPoliceId;

}
