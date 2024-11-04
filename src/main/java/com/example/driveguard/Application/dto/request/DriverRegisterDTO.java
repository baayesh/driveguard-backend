package com.example.driveguard.Application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverRegisterDTO {
    private String firstName;
    private String lastName;
    private String username;
    private Integer age;
    private String licenceNumber;
    public String nic;
    public String houseNumber;
    public String streetName;
    public String city;
    private String password;
    private String allowedVehicleId;
    private String nearestPoliceStationId;
    private Integer offenceLevel;
}
