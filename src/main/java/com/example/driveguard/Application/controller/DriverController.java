package com.example.driveguard.Application.controller;
import com.example.driveguard.Application.dto.request.DriverRegisterDTO;
import com.example.driveguard.Application.dto.response.DriverDataDTO;
import com.example.driveguard.Domain.entity.Driver;
import com.example.driveguard.Domain.service.DriverService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.driveguard.Application.dto.request.login.DriverLoginDTO;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("api/v1.0/driver")
public class DriverController {

    private DriverService driverService;


    @GetMapping("/getDriver")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public ResponseEntity<DriverDataDTO> getDriver(@RequestParam Integer userId) {
        return driverService.getDriver(userId);
    }

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public ResponseEntity<Driver> registerDriver (@RequestBody DriverRegisterDTO driverRegisterDTO){
        return driverService.registerDriver(driverRegisterDTO);
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public ResponseEntity<Object> loginDriver(@RequestBody DriverLoginDTO driverLoginDTO){
        return driverService.loginDriver(driverLoginDTO);
    }

    @GetMapping("/getWitnessedOffences")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public ResponseEntity<List> getWitnessedOffences (@RequestParam Integer userId){
        return driverService.witnessedOffencesList(userId);
    }

    @GetMapping("/getAcceptedOffences")
    public ResponseEntity<List> getAcceptedOffences(@RequestParam Integer userId){
        return driverService.driverAcceptedOffenceList(userId);
    }


}
