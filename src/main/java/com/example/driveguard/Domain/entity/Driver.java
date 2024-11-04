package com.example.driveguard.Domain.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "driver")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer driverId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Integer age;
    private String licenceNumber;
    private String nic;
    private String houseNumber;
    private String streetName;
    private String city;
    private String allowedVehicleId;
    private String nearestPoliceStationId;
    private Integer OffenseLevel;
}
