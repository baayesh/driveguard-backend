package com.example.driveguard.External;

import com.example.driveguard.Domain.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;
@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
}
