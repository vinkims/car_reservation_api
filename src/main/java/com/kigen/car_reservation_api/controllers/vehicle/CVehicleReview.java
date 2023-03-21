package com.kigen.car_reservation_api.controllers.vehicle;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.dtos.vehicle.VehicleReviewDTO;
import com.kigen.car_reservation_api.exceptions.NotFoundException;
import com.kigen.car_reservation_api.models.vehicle.EVehicleReview;
import com.kigen.car_reservation_api.responses.SuccessPaginatedResponse;
import com.kigen.car_reservation_api.responses.SuccessResponse;
import com.kigen.car_reservation_api.services.vehicle.IVehicleReview;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Vehicle Review", description = "Vehicle Review Endpoints")
public class CVehicleReview {
    
    @Autowired
    private IVehicleReview sVehicleReview;

    @PostMapping(path = "/vehicle/review", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createReview(@RequestBody VehicleReviewDTO vehicleReviewDTO) throws URISyntaxException {

        EVehicleReview vehicleReview = sVehicleReview.create(vehicleReviewDTO);

        return ResponseEntity
            .created(new URI("/vehicle/review/" + vehicleReview.getId()))
            .body(new SuccessResponse(201, "successfully created vehicle review", new VehicleReviewDTO(vehicleReview)));
    }

    @GetMapping(path = "/vehicle/review", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getReviews(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException {

        PageDTO pageDTo = new PageDTO(params);

        List<String> allowableFields = new ArrayList<>(Arrays.asList(
            "createdOn", "modifiedOn", "review", "vehicle.id", "user.id", "status.id"
        ));

        Page<EVehicleReview> reviews = sVehicleReview.getPaginatedList(pageDTo, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched reviews", reviews, 
                VehicleReviewDTO.class, EVehicleReview.class));
    }

    @GetMapping(path = "/vehicle/review/{id}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getReview(@PathVariable Integer id) {

        Optional<EVehicleReview> reviewOpt = sVehicleReview.getById(id);
        if (!reviewOpt.isPresent()) {
            throw new NotFoundException("vehicle review with specified id not found", "vehicleReviewId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched vehicle review", new VehicleReviewDTO(reviewOpt.get())));
    }

    @PatchMapping(path = "/vehicle/review/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateReview(@PathVariable Integer id, @RequestBody VehicleReviewDTO vehicleReviewDTO) {

        Optional<EVehicleReview> reviewOpt = sVehicleReview.getById(id);
        if (!reviewOpt.isPresent()) {
            throw new NotFoundException("vehicle review with specified id not found", "vehicleReviewId");
        }

        EVehicleReview review = sVehicleReview.update(reviewOpt.get(), vehicleReviewDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated review", new VehicleReviewDTO(review)));
    }
}
