package com.example.driveguard.Application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfficerDataDTO {
    private String firstName;
    private String lastName;
    private Integer policeIdNumber;
    private String policeStationId;
    private String Post;

}
