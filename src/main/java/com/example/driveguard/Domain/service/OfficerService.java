package com.example.driveguard.Domain.service;

import com.example.driveguard.Application.dto.request.OfficerRegisterDTO;
import com.example.driveguard.Application.dto.response.DriverDataOfficerRequestDTO;
import com.example.driveguard.Application.dto.response.OfficerDataDTO;
import com.example.driveguard.Application.dto.response.WitnessedFineListDTO;
import com.example.driveguard.Domain.entity.Driver;
import com.example.driveguard.Domain.entity.Fine;
import com.example.driveguard.Domain.entity.TrafficOfficer;
import com.example.driveguard.External.DriverRepository;
import com.example.driveguard.External.FineListRepository;
import com.example.driveguard.External.FineRepository;
import com.example.driveguard.External.OfficerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OfficerService {
    public final OfficerRepository officerRepository;
    public final DriverRepository driverRepository;
    public final FineRepository fineRepository;
    public final FineListRepository fineListRepository;

    public ResponseEntity<TrafficOfficer> registerOfficer(OfficerRegisterDTO officerRegisterDTO) {
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
        if (optionalTrafficOfficer.isPresent()) {
            TrafficOfficer trafficOfficer = optionalTrafficOfficer.get();
            officerDataDTO.setFirstName(trafficOfficer.getFirstName());
            officerDataDTO.setLastName(trafficOfficer.getLastName());
            officerDataDTO.setPoliceIdNumber(trafficOfficer.getPoliceIdNumber());
            officerDataDTO.setPoliceStationId(trafficOfficer.getPoliceStationId());
            officerDataDTO.setPost(trafficOfficer.getPost());
            return new ResponseEntity<>(officerDataDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //    get driver data using Licence number
    public ResponseEntity<DriverDataOfficerRequestDTO> getDriver(String licenceNumber) {
        DriverDataOfficerRequestDTO driverDataOfficerRequestDTO = new DriverDataOfficerRequestDTO();
        Optional<Driver> optionalDriver = driverRepository.findDriverByLicenceNumber(licenceNumber);
        if (optionalDriver.isPresent()) {
            Driver driver = optionalDriver.get();
            driverDataOfficerRequestDTO.setFirstName(driver.getFirstName());
            driverDataOfficerRequestDTO.setLastName(driver.getLastName());
            driverDataOfficerRequestDTO.setAge(driver.getAge());
            driverDataOfficerRequestDTO.setHouseNumber(driver.getHouseNumber());
            driverDataOfficerRequestDTO.setStreetName(driver.getStreetName());
            driverDataOfficerRequestDTO.setCity(driver.getCity());
            driverDataOfficerRequestDTO.setNic(driver.getNic());
            return new ResponseEntity<>(driverDataOfficerRequestDTO, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //    get issued offences to make them witnessed
    public ResponseEntity<List<WitnessedFineListDTO>> getFinesToWitness(Integer witnessedOfficerId) {
        if (witnessedOfficerId != null) {
            List<Fine> fineList = fineRepository.getFinesBySupportingOfficerIdAndFineStatus(witnessedOfficerId, "issued");

            if (!fineList.isEmpty()) {
                List<WitnessedFineListDTO> witnessedFineListDTOS = fineList.stream().map(fine -> {
                    WitnessedFineListDTO witnessedFineListDTO = new WitnessedFineListDTO();

                    // Set values
                    witnessedFineListDTO.setFineId(fine.getFineId());
                    witnessedFineListDTO.setFineDate(fine.getFineDate());
                    witnessedFineListDTO.setRemainingDaysToPay(fine.getRemainingDaysToPay());

                    // Retrieve and set FineList details
                    fineListRepository.findByFineListId(fine.getFineListId()).ifPresent(fineDetails -> {
                        witnessedFineListDTO.setFineName(fineDetails.getFineName());
                        witnessedFineListDTO.setFineDescription(fineDetails.getFineDescription());
                        witnessedFineListDTO.setFineAmount(fineDetails.getFineAmount());
                    });

                    // Retrieve and set TrafficOfficer details
                    officerRepository.findByOfficerId(fine.getOfficerId()).ifPresent(trafficOfficer -> {
                        witnessedFineListDTO.setOfficerFirstName(trafficOfficer.getFirstName());
                        witnessedFineListDTO.setOfficerLastName(trafficOfficer.getLastName());
                    });

                    return witnessedFineListDTO;
                }).collect(Collectors.toList());

                if (witnessedFineListDTOS.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }

                return new ResponseEntity<>(witnessedFineListDTOS, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

