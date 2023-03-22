package com.kigen.car_reservation_api.dtos.vehicle;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.dtos.address.LocationDTO;
import com.kigen.car_reservation_api.dtos.status.StatusDTO;
import com.kigen.car_reservation_api.dtos.user.UserDTO;
import com.kigen.car_reservation_api.models.vehicle.EReservation;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ReservationDTO {
    
    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private Integer id;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime createdOn;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime modifiedOn;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    @JsonIgnoreProperties(value = {"createdOn", "contacts", "civilIdentites", "role", "modifiedOn", "lastActiveOn", "status"})
    private UserDTO user;

    private Integer userId;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    @JsonIgnoreProperties(value = {"createdOn", "bookingAmount", "color", "status", "lastActiveOn", "modifiedOn"})
    private VehicleDTO vehicle;

    private Integer vehicleId;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocationDTO pickupLocation;

    private Integer pickupLocationId;

    private Date pickupDate;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocationDTO dropoffLocation;

    private Integer dropoffLocationId;

    private Date dropoffDate;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private StatusDTO status;

    private Integer statusId;

    public ReservationDTO(EReservation reservation) {
        setCreatedOn(reservation.getCreatedOn());
        setDropoffDate(reservation.getDropoffDate());
        setDropoffLocation(new LocationDTO(reservation.getDropoffLocation()));
        setId(reservation.getId());
        setModifiedOn(reservation.getModifiedOn());
        setPickupDate(reservation.getPickupDate());
        setPickupLocation(new LocationDTO(reservation.getPickupLocation()));
        setStatus(new StatusDTO(reservation.getStatus()))
        setUser(new UserDTO(reservation.getUser()));
        setVehicle(new VehicleDTO(reservation.getVehicle()));
    }

}
