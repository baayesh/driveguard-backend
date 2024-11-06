package com.example.driveguard.External;

import com.example.driveguard.Domain.entity.Fine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FineRepository extends JpaRepository<Fine, Integer> {
//    find by driverId and fineStatus
    List<Fine> findByDriverIdAndFineStatus(Integer driverId, String fineStatus);
    Optional<Fine> getFineByFineId(Integer fineId);
    List<Fine> getFineByDriverId(Integer driverId);
    Integer countByFineStatus(String status);

}
