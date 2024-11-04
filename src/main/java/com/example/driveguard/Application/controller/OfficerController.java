package com.example.driveguard.Application.controller;

import com.example.driveguard.Application.dto.response.OfficerDataDTO;
import com.example.driveguard.Domain.entity.TrafficOfficer;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.driveguard.Application.dto.request.OfficerRegisterDTO;
import com.example.driveguard.Domain.service.OfficerService;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1.0/trafficOfficer")
public class OfficerController {

    private OfficerService officerService;

    @PostMapping("/register")
    public ResponseEntity<TrafficOfficer> registerTrafficOfficer (@RequestBody OfficerRegisterDTO officerRegisterDTO){
        return officerService.registerOfficer(officerRegisterDTO);
    }

    @GetMapping("/get")
    public ResponseEntity<OfficerDataDTO> getOfficer(@RequestParam Integer policeIdNumber ){
        return officerService.getOfficer(policeIdNumber);
    }
}
