package com.example.driveguard.Application.controller;

import com.example.driveguard.Application.dto.request.CreateFineDTO;
import com.example.driveguard.Application.dto.request.OffenceLevel.FineStatusAndOffenceLevelDTO;
import com.example.driveguard.Application.dto.response.FineDataDTO;
import com.example.driveguard.Domain.entity.Fine;
import com.example.driveguard.Domain.entity.FineList;
import com.example.driveguard.Domain.service.DriverService;
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
    public ResponseEntity<Object> createFine(@RequestBody CreateFineDTO createFineDTO) {
        ResponseEntity<Object> responseEntity = fineService.createFine(createFineDTO);
        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() instanceof Fine) {
            Fine fine = (Fine) responseEntity.getBody();
            fineService.increaseOffenceValue(fine.getDriverId(), fine.getFineListId());
        }
        return responseEntity;
    }

//    get fine from fine list ## based id
        @GetMapping("/get")
        public ResponseEntity<FineList> getFine (@RequestParam Integer fineId){
            return fineService.getFineById(fineId);
        }
//    get issued fine from fines table using fineId
        @GetMapping("get/fine")
        public ResponseEntity<FineDataDTO> getIssuedFine (@RequestParam Integer fineId){
            return fineService.getFine(fineId);
        }

//    get all fines
        @GetMapping("/get/list")
        public List<FineList> getFineList () {
            return fineService.getFineList();
        }

//    get fine ##based on driverId and fineStatus
        @GetMapping("/get/fineStatus")
        public ResponseEntity<List<Fine>> getAcceptedFines (@RequestParam Integer driverId, String fineStatus){
            return fineService.getAcceptedFines(driverId, fineStatus);
        }

//    change status to paid and decrease offence level
        @PostMapping("/makePayment")
        public ResponseEntity<String> changeFineStatusToPaidAndDecreaseOffenceLevel
        (@RequestBody FineStatusAndOffenceLevelDTO fineStatusAndOffenceLevelDTO){
            return fineService.changeFineStatusAndDecreaseOffenceLevel(fineStatusAndOffenceLevelDTO.getFineId(), fineStatusAndOffenceLevelDTO.getDriverId(), fineStatusAndOffenceLevelDTO.getFineListId());
        }

//    get driver history
        @GetMapping("get/driverHistory")
        public ResponseEntity<List<Fine>> getDriverHistory (@RequestParam Integer driverId){
            return fineService.driverHistory(driverId);
        }

    }
