package com.kigen.car_reservation_api.models.vehicle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.kigen.car_reservation_api.models.status.EStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "vehicles")
@Data
@NoArgsConstructor
public class EVehicle implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Column(name = "booking_amount")
    private BigDecimal bookingAmount;

    @Column(name = "color")
    private String color;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "engine_capacity")
    private Integer engineCapacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fuel_type_id", referencedColumnName = "id")
    private EFuelType fuelType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private Integer id;

    @Column(name = "last_active_on")
    private LocalDateTime lastActiveOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_model_id", referencedColumnName = "id")
    private EVehicleModel model;

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    @Column(name = "registration_number")
    private String registrationNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private EStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transmission_type_id", referencedColumnName = "id")
    private ETransmissionType transmissionType;

    public void setBookingAmount(BigDecimal amount) {
        bookingAmount = bookingAmount != null ? bookingAmount : new BigDecimal("0");
        if (amount != null) {
            bookingAmount = bookingAmount.add(amount);
        }
    }
}
