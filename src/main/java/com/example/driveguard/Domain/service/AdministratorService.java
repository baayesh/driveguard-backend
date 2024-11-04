package com.example.driveguard.Domain.service;

import com.example.driveguard.Application.dto.request.AdministratorRegisterDTO;
import com.example.driveguard.Domain.entity.Administrator;
import com.example.driveguard.External.AdministratorRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdministratorService {

    private final AdministratorRepository administratorRepository;

    public ResponseEntity<Administrator> registerAdministrator(AdministratorRegisterDTO administratorRegisterDTO){
        Administrator administrator = new Administrator();
        administrator.setUsername(administratorRegisterDTO.getUsername());
        administrator.setPoliceStationId(administratorRegisterDTO.getPoliceStationId());
        administrator.setPassword(administratorRegisterDTO.getPassword());
        administratorRepository.save(administrator);
        return new ResponseEntity<>(administrator, HttpStatus.CREATED);
    }


}
