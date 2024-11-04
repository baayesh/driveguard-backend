package com.example.driveguard.External;

import com.example.driveguard.Domain.entity.Driver;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Integer> {

//    find user by username
    Optional<Driver> findByUsername(String username);
    Optional<Driver> findDriverByDriverId(Integer driverId);


}