package com.example.driveguard.Application.controller;
import com.example.driveguard.Application.dto.request.DriverRegisterDTO;
import com.example.driveguard.Application.dto.response.DriverDataDTO;
import com.example.driveguard.Domain.entity.Driver;
import com.example.driveguard.Domain.service.DriverService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.driveguard.Application.dto.request.login.DriverLoginDTO;


@RestController
@AllArgsConstructor
@RequestMapping("api/v1.0/driver")
public class DriverController {

    private DriverService driverService;


    @GetMapping("/getDriver")
    public ResponseEntity<DriverDataDTO> getDriver(@RequestParam Integer userId) {
        return driverService.getDriver(userId);
    }

    @PostMapping("/register")
    public ResponseEntity<Driver> registerDriver (@RequestBody DriverRegisterDTO driverRegisterDTO){
        return driverService.registerDriver(driverRegisterDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginDriver(@RequestBody DriverLoginDTO driverLoginDTO){
        return driverService.loginDriver(driverLoginDTO);
    }


}
