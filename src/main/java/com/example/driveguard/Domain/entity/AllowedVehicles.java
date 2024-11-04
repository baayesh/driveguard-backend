package com.example.driveguard.Domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "allowed_vehicles")
public class AllowedVehicles {
    @Id
    private Integer allowed_vehicle_id;
    private Boolean A1;
    private Boolean A;
    private Boolean B1;
    private Boolean B;
    private Boolean C1;
    private Boolean C;
    private Boolean D1;
    private Boolean D;
    private Boolean DE;
    private Boolean G1;
    private Boolean G;
    private Boolean J;

}
