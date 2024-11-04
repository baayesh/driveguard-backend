package com.example.driveguard.Application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorRegisterDTO {
    private String username;
    private String policeStationId;
    private String password;
}
