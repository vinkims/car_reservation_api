package com.kigen.car_reservation_api.dtos.vehicle;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.dtos.status.StatusDTO;
import com.kigen.car_reservation_api.models.vehicle.EReservation;
import com.kigen.car_reservation_api.models.vehicle.EVehicle;
import com.kigen.car_reservation_api.models.vehicle.EVehicleReview;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class VehicleDTO {
    
    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private Integer id;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime createdOn;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime lastActiveOn;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime modifiedOn;

    private String registrationNumber;

    private String vehicleMake;

    private String vehicleModel;

    private String color;

    private Integer engineCapacity;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    @JsonIgnoreProperties(value = {"createdOn", "description"})
    private TransmissionTypeDTO transmissionType;

    private Integer transmissionTypeId;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    @JsonIgnoreProperties(value = {"createdOn", "description"})
    private FuelTypeDTO fuelType;

    private Integer fuelTypeId;

    private BigDecimal bookingAmount;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    @JsonIgnoreProperties(value = {"createdOn", "description"})
    private StatusDTO status;

    private Integer statusId;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    @JsonIgnoreProperties(value = {"vehicle"})
    private List<ReservationDTO> reservations;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    @JsonIgnoreProperties(value = {"vehicle"})
    private List<VehicleReviewDTO> reviews;

    public VehicleDTO(EVehicle vehicle) {
        setBookingAmount(vehicle.getBookingAmount());
        setColor(vehicle.getColor());
        setCreatedOn(vehicle.getCreatedOn());
        setEngineCapacity(vehicle.getEngineCapacity());
        setFuelType(new FuelTypeDTO(vehicle.getFuelType()));
        setId(vehicle.getId());
        setLastActiveOn(vehicle.getLastActiveOn());
        setModifiedOn(vehicle.getModifiedOn());
        setRegistrationNumber(vehicle.getRegistrationNumber());
        setReservationData(vehicle.getReservations());
        setReviewData(vehicle.getReviews());
        setStatus(new StatusDTO(vehicle.getStatus()));
        setTransmissionType(new TransmissionTypeDTO(vehicle.getTransmissionType()));
        if (vehicle.getModel() != null) {
            setVehicleMake(vehicle.getModel().getVehicleMake());
            setVehicleModel(vehicle.getModel().getVehicleModel());
        }
    }

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    public void setReservationData(List<EReservation> reservationList) {
        if (reservationList == null || reservationList.isEmpty()) { return; }

        reservations = new ArrayList<>();
        for (EReservation reservation : reservationList) {
            reservations.add(new ReservationDTO(reservation));
        }
    }

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    public void setReviewData(List<EVehicleReview> reviewList) {
        if (reviewList == null || reviewList.isEmpty()) { return; }

        reviews = new ArrayList<>();
        for (EVehicleReview review : reviewList) {
            reviews.add(new VehicleReviewDTO(review));
        }
    }
}
