package com.example.driveguard.Domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "traffic_officer")
public class TrafficOfficer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer officerId;
    private String firstName;
    private String lastName;
    private String password;
    private Integer policeIdNumber;
    private String post;
    private String policeStationId;

}
