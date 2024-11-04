package com.example.driveguard.Application.controller;
import com.example.driveguard.Application.dto.request.AdministratorRegisterDTO;
import com.example.driveguard.Domain.entity.Administrator;
import com.example.driveguard.Domain.service.AdministratorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("api/v1.0/administrator")
public class AdministratorController {
    private AdministratorService administratorService;

    @PostMapping("/register")
    public ResponseEntity<Administrator> registerAdministrator(@RequestBody AdministratorRegisterDTO administratorRegisterDTO){
        return administratorService.registerAdministrator(administratorRegisterDTO);
    }


}
