package com.example.driveguard.Domain.service;

import com.example.driveguard.Application.dto.request.CreateFineDTO;
import com.example.driveguard.Application.dto.response.FineDataDTO;
import com.example.driveguard.Domain.entity.Driver;
import com.example.driveguard.Domain.entity.Fine;
import com.example.driveguard.Domain.entity.FineList;
import com.example.driveguard.Domain.entity.TrafficOfficer;
import com.example.driveguard.External.DriverRepository;
import com.example.driveguard.External.FineListRepository;
import com.example.driveguard.External.FineRepository;
import com.example.driveguard.External.OfficerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FineService {
    public final FineRepository fineRepository;
    public final FineListRepository fineListRepository;
    private final DriverRepository driverRepository;
    private final OfficerRepository officerRepository;

    public ResponseEntity<Object> createFine(CreateFineDTO createFineDTO) {
        Fine fine = new Fine();
        Optional<Driver> optionalDriver = driverRepository.findDriverByLicenceNumber(createFineDTO.getLicenceNumber());
        if (optionalDriver.isPresent()) {
            Optional<FineList> optionalFine = fineListRepository.findByFineListId(createFineDTO.getFineListId());
            if (optionalFine.isPresent()) {
                System.out.println(createFineDTO.getWitnessOfficerId());
                System.out.println(createFineDTO.getFineListId());
                System.out.println(createFineDTO.getLicenceNumber());
                Optional<TrafficOfficer> optionalTrafficOfficer = officerRepository.findByPoliceIdNumber(createFineDTO.getWitnessOfficerId());
                if (optionalTrafficOfficer.isPresent()) {
                    Driver driver = optionalDriver.get();
                    FineList fineList = optionalFine.get();
                    TrafficOfficer trafficOfficer = optionalTrafficOfficer.get();
                    Integer driverId = driver.getDriverId();
                    LocalDate today = LocalDate.now();
                    Integer fineListId = fineList.getFineListId();
                    String fineStatus = "issued";
                    Integer officerId = createFineDTO.getOfficerId();
                    Integer remainingDaysToPay = 14;
                    Integer witnessedOfficerId = trafficOfficer.getOfficerId();
//                set entity values
                    fine.setDriverId(driverId);
                    fine.setFineDate(today);
                    fine.setFineListId(fineListId);
                    fine.setFineStatus(fineStatus);
                    fine.setOfficerId(officerId);
                    fine.setRemainingDaysToPay(remainingDaysToPay);
                    fine.setSupportingOfficerId(witnessedOfficerId);
//                save and send response
                    fineRepository.save(fine);
                    return new ResponseEntity<>(fine, HttpStatus.CREATED);
                }
                else{
                    String message ="no officer found";
                    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
                }


            } else {
                String message ="no fine found";
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            }
        } else {
            String message ="no driver found";
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    public ResponseEntity<FineList> getFineById(Integer fineId) {
        Optional<FineList> optionalFineList = fineListRepository.findByFineListId(fineId);
        return optionalFineList.map(fineList -> new ResponseEntity<>(fineList, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //    list of fines
    public List<FineList> getFineList() {
        return fineListRepository.findAll();
    }

    //    Increase offence value when create fine
    public void increaseOffenceValue(Integer driverId, Integer fineListId) {
        if (driverId != null && fineListId != null) {
            Optional<FineList> optionalFine = fineListRepository.findByFineListId(fineListId);
            if (optionalFine.isPresent()) {
                FineList fine = optionalFine.get();
                Integer offenceChangeValue = fine.getOffenceChangeValue();
                Optional<Driver> optionalDriver = driverRepository.findById(driverId);
                if (optionalDriver.isPresent()) {
                    Driver driver = optionalDriver.get();
                    Integer currentOffenceLevel = driver.getOffenseLevel();
                    currentOffenceLevel += offenceChangeValue;
                    if (currentOffenceLevel > 5 || currentOffenceLevel < 0) {
                        System.out.println("can't Increase values");
                    } else {
                        driver.setOffenseLevel(currentOffenceLevel);
                        driverRepository.save(driver);
                    }

                }

            }
        }
    }

    //    get fine by driverId and status
    public ResponseEntity<List<Fine>> getAcceptedFines(Integer driverId, String fineStatus) {
        if (driverId != null && fineStatus != null) {
            List<Fine> fines = fineRepository.findByDriverIdAndFineStatus(driverId, fineStatus);
            if (!fines.isEmpty()) {
                return new ResponseEntity<>(fines, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //    change fine Status to paid
    public ResponseEntity<String> changeFineStatusAndDecreaseOffenceLevel(Integer fineId, Integer driverId, Integer fineListId) {
        if (fineId != null) {
            Optional<Fine> optionalFine = fineRepository.getFineByFineId(fineId);
            if (optionalFine.isPresent()) {
                Fine fine = optionalFine.get();
                if (fine.getFineStatus().equals("accepted")) {
                    fine.setFineStatus("paid");
                    fineRepository.save(fine);
                    Optional<FineList> optionalFineList = fineListRepository.findByFineListId(fineListId);
                    if (optionalFineList.isPresent()) {
                        Optional<Driver> optionalDriver = driverRepository.findDriverByDriverId(driverId);
                        if (optionalDriver.isPresent()) {
                            Driver driver = optionalDriver.get();
                            FineList fineList = optionalFineList.get();
                            Integer currentOffenceLevel = driver.getOffenseLevel();
                            Integer offenceChangeable = fineList.getOffenceChangeValue();
                            currentOffenceLevel -= offenceChangeable;
                            if (currentOffenceLevel > 5 || currentOffenceLevel < 0) {
                                System.out.println("can't decrease value");
                            } else {
                                driver.setOffenseLevel(currentOffenceLevel);
                                driverRepository.save(driver);
                            }

                        } else {
                            return new ResponseEntity<>("No Such driver", HttpStatus.NOT_FOUND);
                        }
                    } else {
                        return new ResponseEntity<>("No Such fine", HttpStatus.NOT_FOUND);
                    }
                    return new ResponseEntity<>("Payment Accepted", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Fine Status is not equal to Accepted", HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<>("No such fine available", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Please enter valid fineId", HttpStatus.BAD_REQUEST);
        }
    }

    //    get fine from fines table. Issued fine will return according to fine id
    public ResponseEntity<FineDataDTO> getFine(Integer fineId) {
        Optional<Fine> optionalFine = fineRepository.getFineByFineId(fineId);
        if (optionalFine.isPresent()) {
            FineDataDTO fineDataDTO = new FineDataDTO();
            Fine fine = optionalFine.get();
            fineDataDTO.setFineId(fine.getFineId());
            fineDataDTO.setOfficerId(fine.getOfficerId());
            fineDataDTO.setSupportingOfficerId(fine.getSupportingOfficerId());
            fineDataDTO.setFineDate(fine.getFineDate());
            fineDataDTO.setRemainingDaysToPay(fine.getRemainingDaysToPay());
            fineDataDTO.setFineStatus(fine.getFineStatus());
            return new ResponseEntity<>(fineDataDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //    get all fines by driver id
    public ResponseEntity<List<Fine>> driverHistory(Integer driverId) {
        List<Fine> fineList = fineRepository.getFinesByDriverId(driverId);
        if (!fineList.isEmpty()) {
            return new ResponseEntity<>(fineList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
