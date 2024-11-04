package com.example.driveguard.Domain.service;

import com.example.driveguard.Application.dto.request.DriverRegisterDTO;
import com.example.driveguard.Application.dto.request.login.DriverLoginDTO;
import com.example.driveguard.Application.dto.response.DriverDataDTO;
import com.example.driveguard.Domain.entity.Driver;
import com.example.driveguard.External.DriverRepository;
import lombok.AllArgsConstructor;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;
//    Login

    public ResponseEntity<DriverDataDTO> getDriver(Integer userId) {
        DriverDataDTO driverDataDTO = new DriverDataDTO();
        Optional<Driver> optionalDriver = driverRepository.findById(userId);
        if (optionalDriver.isPresent()) {
            Driver driver = optionalDriver.get();
            driverDataDTO.setFirstName(driver.getFirstName());
            driverDataDTO.setLastName(driver.getLastName());
            driverDataDTO.setAge(driver.getAge());
            driverDataDTO.setAllowedVehicleId(driver.getAllowedVehicleId());
            driverDataDTO.setNearestPoliceStationId(driver.getNearestPoliceStationId());
            driverDataDTO.setOffenceLevel(driver.getOffenseLevel());
            return new ResponseEntity<>(driverDataDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    public ResponseEntity<Driver> registerDriver(DriverRegisterDTO driverRegisterDTO) {
        Driver driver = new Driver();
        driver.setFirstName(driverRegisterDTO.getFirstName());
        driver.setLastName(driverRegisterDTO.getLastName());
        driver.setUsername(driverRegisterDTO.getUsername());
        driver.setAge(driverRegisterDTO.getAge());
        driver.setLicenceNumber(driverRegisterDTO.getLicenceNumber());
        driver.setNic(driverRegisterDTO.getNic());
        driver.setHouseNumber(driverRegisterDTO.getHouseNumber());
        driver.setStreetName(driverRegisterDTO.getStreetName());
        driver.setCity(driverRegisterDTO.getCity());
        driver.setPassword(driverRegisterDTO.getPassword());
        driver.setAllowedVehicleId(driverRegisterDTO.getAllowedVehicleId());
        driver.setNearestPoliceStationId(driverRegisterDTO.getNearestPoliceStationId());
        driver.setOffenseLevel(0);
        driverRepository.save(driver);
        return new ResponseEntity<>(driver, HttpStatus.CREATED);

    }

    //login
    public ResponseEntity<String> loginDriver(DriverLoginDTO driverLoginDTO) {
        Optional<Driver> optionalDriver = driverRepository.findByUsername(driverLoginDTO.getUsername());
        if (optionalDriver.isPresent()) {
            Driver driver = optionalDriver.get();
            if (driver.getPassword().equals(driverLoginDTO.getPassword())) {
                return new ResponseEntity<>("Login Successful", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Unauthorized login", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
