package com.example.driveguard.Application.controller;

import com.example.driveguard.Application.dto.request.login.OfficerLoginDTO;
import com.example.driveguard.Application.dto.response.DriverDataOfficerRequestDTO;
import com.example.driveguard.Application.dto.response.IssuedFineListDTO;
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
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public ResponseEntity<TrafficOfficer> registerTrafficOfficer (@RequestBody OfficerRegisterDTO officerRegisterDTO){
        return officerService.registerOfficer(officerRegisterDTO);
    }

    @GetMapping("/get")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public ResponseEntity<OfficerDataDTO> getOfficer(@RequestParam Integer policeIdNumber ){
        return officerService.getOfficer(policeIdNumber);
    }

    @GetMapping("/get/driver")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public ResponseEntity<DriverDataOfficerRequestDTO> getDriver(@RequestParam String licenceNumber){
        return officerService.getDriver(licenceNumber);
    }

    @GetMapping("get/witnessingOffencesList")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public ResponseEntity<List<IssuedFineListDTO>> getWitnessedOfficerList (@RequestParam Integer witnessedOfficerId){
        return officerService.getFinesToWitness(witnessedOfficerId);
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public ResponseEntity<Object> officerLogin(@RequestBody OfficerLoginDTO officerLoginDTO){
        return officerService.officerLogin(officerLoginDTO);
    }


}
