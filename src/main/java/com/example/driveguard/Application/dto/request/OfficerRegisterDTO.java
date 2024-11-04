package com.example.driveguard.Application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfficerRegisterDTO {
    private String firstName;
    private String lastName;
    private String password;
    private Integer policeIdNumber;
    private String post;
    private String policeStationId;
}
