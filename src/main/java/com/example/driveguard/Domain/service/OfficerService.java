package com.example.driveguard.Domain.service;

import com.example.driveguard.Application.dto.request.OfficerRegisterDTO;
import com.example.driveguard.Application.dto.request.login.OfficerLoginDTO;
import com.example.driveguard.Application.dto.response.DriverDataOfficerRequestDTO;
import com.example.driveguard.Application.dto.response.IssuedFineListDTO;
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

import java.util.HashMap;
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

//    officer login
    public ResponseEntity<Object> officerLogin(OfficerLoginDTO officerLoginDTO) {
        Optional<TrafficOfficer> optionalTrafficOfficer = officerRepository.findByPoliceIdNumber(officerLoginDTO.getPoliceIdNumber());
        if (optionalTrafficOfficer.isPresent()) {
            TrafficOfficer trafficOfficer = optionalTrafficOfficer.get();
            if (trafficOfficer.getPassword().equals(officerLoginDTO.getPassword())) {

//          create hashmap to send data
                HashMap<String, Object> response = new HashMap<>();
                response.put("Message", "login Successful");
                response.put("OfficerId", trafficOfficer.getOfficerId());

                return new ResponseEntity<>( response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Unauthorized login", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Bad request",  HttpStatus.NOT_FOUND);
        }
    }

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
            driverDataOfficerRequestDTO.setOffenseLevel(driver.getOffenseLevel());
            return new ResponseEntity<>(driverDataOfficerRequestDTO, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //    get issued offences to make them witnessed
    public ResponseEntity<List<IssuedFineListDTO>> getFinesToWitness(Integer witnessedOfficerId) {
        if (witnessedOfficerId != null) {
            List<Fine> fineList = fineRepository.getFinesBySupportingOfficerIdAndFineStatus(witnessedOfficerId, "issued");

            if (!fineList.isEmpty()) {
                List<IssuedFineListDTO> issuedFineListDTOS = fineList.stream().map(fine -> {
                    IssuedFineListDTO issuedFineListDTO = new IssuedFineListDTO();

                    // Set direct values
                    issuedFineListDTO.setOffenseId(fine.getFineId());
                    issuedFineListDTO.setOffenceDate(fine.getFineDate());

                    // Retrieve and set fine list data
                    fineListRepository.findByFineListId(fine.getFineListId()).ifPresent(fineDetail -> {
                        issuedFineListDTO.setOffenseName(fineDetail.getFineName());
                        issuedFineListDTO.setOffenseDescription(fineDetail.getFineDescription());
                        issuedFineListDTO.setOffenseAmount(fineDetail.getFineAmount());
                    });

//                    set driver data
                    driverRepository.findDriverByDriverId(fine.getDriverId()).ifPresent(driver -> {
                        issuedFineListDTO.setDriverFirstName(driver.getFirstName());
                        issuedFineListDTO.setDriverLastName(driver.getLastName());
                        issuedFineListDTO.setDrivingLicenseNumber(driver.getLicenceNumber());
                    });

                    // Retrieve and set officer details
                    officerRepository.findById(fine.getOfficerId()).ifPresent(issuedOfficer -> {
                        issuedFineListDTO.setIssuedOfficerFirstName(issuedOfficer.getFirstName());
                        issuedFineListDTO.setIssuedOfficerLastName(issuedOfficer.getLastName());
                        issuedFineListDTO.setIssuedOfficerPoliceId(issuedOfficer.getPoliceIdNumber());
                    });

                    return issuedFineListDTO;
                }).collect(Collectors.toList());

                return new ResponseEntity<>(issuedFineListDTOS, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}

