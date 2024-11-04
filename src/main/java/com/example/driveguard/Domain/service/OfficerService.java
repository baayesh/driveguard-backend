package com.example.driveguard.Domain.service;

import com.example.driveguard.Application.dto.request.OfficerRegisterDTO;
import com.example.driveguard.Application.dto.response.OfficerDataDTO;
import com.example.driveguard.Domain.entity.TrafficOfficer;
import com.example.driveguard.External.OfficerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OfficerService {
    public final OfficerRepository officerRepository;

    public ResponseEntity<TrafficOfficer> registerOfficer (OfficerRegisterDTO officerRegisterDTO){
        TrafficOfficer trafficOfficer = new TrafficOfficer();
        trafficOfficer.setFirstName(officerRegisterDTO.getFirstName());
        trafficOfficer.setLastName(officerRegisterDTO.getLastName());
        trafficOfficer.setPassword(officerRegisterDTO.getPassword());
        trafficOfficer.setPoliceIdNumber(officerRegisterDTO.getPoliceIdNumber());
        trafficOfficer.setPost(officerRegisterDTO.getPost());
        trafficOfficer.setPoliceStationId(officerRegisterDTO.getPoliceStationId());
        officerRepository.save(trafficOfficer);
        return new ResponseEntity<>(trafficOfficer, HttpStatus.CREATED);
    }


    public ResponseEntity<OfficerDataDTO> getOfficer(Integer policeIdNumber) {
        OfficerDataDTO officerDataDTO = new OfficerDataDTO();
        Optional<TrafficOfficer> optionalTrafficOfficer = officerRepository.findByPoliceIdNumber(policeIdNumber);
        if(optionalTrafficOfficer.isPresent()){
            TrafficOfficer trafficOfficer = optionalTrafficOfficer.get();
            officerDataDTO.setFirstName(trafficOfficer.getFirstName());
            officerDataDTO.setLastName(trafficOfficer.getLastName());
            officerDataDTO.setPoliceIdNumber(trafficOfficer.getPoliceIdNumber());
            officerDataDTO.setPoliceStationId(trafficOfficer.getPoliceStationId());
            officerDataDTO.setPost(trafficOfficer.getPost());
            return new ResponseEntity<>(officerDataDTO, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
