package com.example.driveguard.External;


import com.example.driveguard.Domain.entity.FineList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FineListRepository extends JpaRepository<FineList, Integer> {
    Optional<FineList> findByFineListId(Integer fineListId);

}
