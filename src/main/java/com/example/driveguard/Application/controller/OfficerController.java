package com.example.driveguard.Application.controller;

import com.example.driveguard.Application.dto.request.login.OfficerLoginDTO;
import com.example.driveguard.Application.dto.response.DriverDataOfficerRequestDTO;
import com.example.driveguard.Application.dto.response.OfficerDataDTO;
import com.example.driveguard.Application.dto.response.WitnessedFineListDTO;
import com.example.driveguard.Domain.entity.TrafficOfficer;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.driveguard.Application.dto.request.OfficerRegisterDTO;
import com.example.driveguard.Domain.service.OfficerService;

import java.util.List;

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

    @GetMapping("/get/driver")
    public ResponseEntity<DriverDataOfficerRequestDTO> getDriver(@RequestParam String licenceNumber){
        return officerService.getDriver(licenceNumber);
    }

    @GetMapping("get/witnessingOffencesList")
    public ResponseEntity<List<WitnessedFineListDTO>> getWitnessedOfficerList (@RequestParam Integer witnessedOfficerId){
        return officerService.getFinesToWitness(witnessedOfficerId);
    }

    @PostMapping("/login")
    public ResponseEntity<TrafficOfficer> officerLogin(@RequestBody OfficerLoginDTO officerLoginDTO){
        System.out.println("called to function");
        return officerService.officerLogin(officerLoginDTO);
    }
}
