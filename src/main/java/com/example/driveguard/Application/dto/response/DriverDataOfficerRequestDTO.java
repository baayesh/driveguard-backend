package com.example.driveguard.Application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverDataOfficerRequestDTO {
    private String firstName;
    private String lastName;
    private Integer age;
    private String houseNumber;
    private String streetName;
    private String city;
    private String nic;

}
