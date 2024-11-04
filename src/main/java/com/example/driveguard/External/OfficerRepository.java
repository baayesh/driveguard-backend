package com.example.driveguard.External;

import com.example.driveguard.Domain.entity.TrafficOfficer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OfficerRepository extends JpaRepository<TrafficOfficer, Integer> {
    Optional<TrafficOfficer> findByPoliceIdNumber(Integer policeIdNumber);
}
