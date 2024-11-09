package com.example.driveguard.Application.controller;
import com.example.driveguard.Application.dto.request.AdministratorRegisterDTO;
import com.example.driveguard.Domain.entity.Administrator;
import com.example.driveguard.Domain.service.AdministratorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("api/v1.0/administrator")
public class AdministratorController {
    private AdministratorService administratorService;

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public ResponseEntity<Administrator> registerAdministrator(@RequestBody AdministratorRegisterDTO administratorRegisterDTO){
        return administratorService.registerAdministrator(administratorRegisterDTO);
    }


}
