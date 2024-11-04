package com.example.driveguard.Application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverDataDTO {
    private String firstName;
    private String lastName;
    private Integer age;
    private String allowedVehicleId;
    private String nearestPoliceStationId;
    private Integer offenceLevel;
}
