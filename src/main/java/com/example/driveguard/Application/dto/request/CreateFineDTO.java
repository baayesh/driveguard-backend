package com.example.driveguard.Application.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreateFineDTO {
   private Integer officerId;
   private String licenceNumber;
   private Integer fineListId;
   private Integer witnessOfficerId;
}
