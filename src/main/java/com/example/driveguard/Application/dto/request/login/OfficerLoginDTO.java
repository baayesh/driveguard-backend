package com.example.driveguard.Application.dto.request.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfficerLoginDTO {
    private Integer policeIdNumber;
    private String password;
}
