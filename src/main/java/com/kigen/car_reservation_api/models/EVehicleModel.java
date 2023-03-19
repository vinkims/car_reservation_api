package com.kigen.car_reservation_api.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "vehicle_models")
@Data
@NoArgsConstructor
public class EVehicleModel implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private Integer id;

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    @Column(name = "vehicle_make")
    private String vehicleMake;

    @Column(name = "vehicle_model")
    private String vehicleModel;
}
