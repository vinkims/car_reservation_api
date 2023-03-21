package com.kigen.car_reservation_api.services.vehicle;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.dtos.vehicle.VehicleReviewDTO;
import com.kigen.car_reservation_api.models.vehicle.EVehicleReview;

public interface IVehicleReview {
    
    EVehicleReview create(VehicleReviewDTO vehicleReviewDTO);

    Optional<EVehicleReview> getById(Integer vehicleReviewId);

    EVehicleReview getById(Integer vehicleReviewId, Boolean handleException);

    Page<EVehicleReview> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(EVehicleReview vehicleReview);

    EVehicleReview update(EVehicleReview vehicleReview, VehicleReviewDTO vehicleReviewDTO);
}
