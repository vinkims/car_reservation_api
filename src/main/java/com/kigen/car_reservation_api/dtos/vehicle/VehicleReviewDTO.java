package com.kigen.car_reservation_api.dtos.vehicle;

import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.dtos.status.StatusDTO;
import com.kigen.car_reservation_api.dtos.user.UserDTO;
import com.kigen.car_reservation_api.models.vehicle.EVehicleReview;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class VehicleReviewDTO {
    
    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private Integer id;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime createdOn;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime modifiedOn;

    @Max(5)
    @Min(1)
    private Integer review;

    private String comment;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private VehicleDTO vehicle;

    @NotNull
    private Integer vehicleId;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    @JsonIgnoreProperties(value = {"createdOn", "contacts", "civilIdentites", "role", "modifiedOn", "lastActiveOn", "status"})
    private UserDTO user;

    private Integer userId;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private StatusDTO status;

    private Integer statusId;

    public VehicleReviewDTO(EVehicleReview vehicleReview) {
        setComment(vehicleReview.getComment());
        setCreatedOn(vehicleReview.getCreatedOn());
        setId(vehicleReview.getId());
        setModifiedOn(vehicleReview.getModifiedOn());
        setReview(vehicleReview.getReview());
        if (vehicleReview.getUser() != null) {
            setUser(new UserDTO(vehicleReview.getUser()));
        }
        setStatus(new StatusDTO(vehicleReview.getStatus()));
        setVehicle(new VehicleDTO(vehicleReview.getVehicle()));
    }
}
