package com.example.driveguard.Application.controller;

import com.example.driveguard.Application.dto.request.CreateFineDTO;
import com.example.driveguard.Application.dto.request.OffenceLevel.FineStatusAndOffenceLevelDTO;
import com.example.driveguard.Application.dto.response.FineDataDTO;
import com.example.driveguard.Application.dto.response.WitnessedFineListDTO;
import com.example.driveguard.Domain.entity.Fine;
import com.example.driveguard.Domain.entity.FineList;
import com.example.driveguard.Domain.service.FineService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("api/v1.0/fine")
public class FineController {

    private FineService fineService;

    //    create fine and increase offence level
    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public ResponseEntity<Object> createFine(@RequestBody CreateFineDTO createFineDTO) {
        return fineService.createFine(createFineDTO);
//        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() instanceof Fine) {
//            Fine fine = (Fine) responseEntity.getBody();
//            fineService.increaseOffenceValue(fine.getDriverId(), fine.getFineListId());
//        }

    }

    //    get fine from fine list ## based id
    @GetMapping("/get")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public ResponseEntity<FineList> getFine(@RequestParam Integer fineId) {
        return fineService.getFineById(fineId);
    }

    //    get issued fine from fines table using fineId
    @GetMapping("get/fine")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public ResponseEntity<FineDataDTO> getIssuedFine(@RequestParam Integer fineId) {
        return fineService.getFine(fineId);
    }

    //    get all fines
    @GetMapping("/get/list")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public List<FineList> getFineList() {
        return fineService.getFineList();
    }

    //    get fine ##based on driverId and fineStatus
    @GetMapping("/get/fineStatus")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public ResponseEntity<List<WitnessedFineListDTO>> getAcceptedFines(@RequestParam Integer driverId, String fineStatus) {
        return fineService.getFines(driverId, fineStatus);
    }

    //    change status to paid and decrease offence level
    @PostMapping("/makePayment")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public ResponseEntity<String> changeFineStatusToPaidAndDecreaseOffenceLevel
    (@RequestBody FineStatusAndOffenceLevelDTO fineStatusAndOffenceLevelDTO) {
        return fineService.changeFineStatusAndDecreaseOffenceLevel(fineStatusAndOffenceLevelDTO.getFineId(), fineStatusAndOffenceLevelDTO.getDriverId(), fineStatusAndOffenceLevelDTO.getFineListId());
    }

    //    get driver history
    @GetMapping("/get/driverHistory")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public ResponseEntity<List<Fine>> getDriverHistory(@RequestParam Integer driverId) {
        return fineService.driverHistory(driverId);
    }

//    accept fine by driver
    @GetMapping("/acceptFine")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public ResponseEntity<String> acceptFine(@RequestParam Integer fineId){
        return fineService.acceptFine(fineId);
    }

    //        make officer witnessed
    @GetMapping("/witnessed")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public ResponseEntity<String> makeOfficerWitnessed(@RequestParam Integer fineId) {
        return fineService.makeWitnessed(fineId);
    }
}
