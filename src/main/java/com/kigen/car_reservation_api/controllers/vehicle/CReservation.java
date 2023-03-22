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
import com.kigen.car_reservation_api.dtos.vehicle.ReservationDTO;
import com.kigen.car_reservation_api.exceptions.NotFoundException;
import com.kigen.car_reservation_api.models.vehicle.EReservation;
import com.kigen.car_reservation_api.responses.SuccessPaginatedResponse;
import com.kigen.car_reservation_api.responses.SuccessResponse;
import com.kigen.car_reservation_api.services.vehicle.IReservation;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Reservations", description = "Reservation Endpoints")
public class CReservation {
    
    @Autowired
    private IReservation sReservation;

    @PostMapping(path = "/reservation", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createReservation(@RequestBody ReservationDTO reservationDTO) throws URISyntaxException {

        EReservation reservation = sReservation.create(reservationDTO);

        return ResponseEntity
            .created(new URI("/reservation/" + reservation.getId()))
            .body(new SuccessResponse(201, "successfully created reservation", new ReservationDTO(reservation)));
    }

    @GetMapping(path = "/reservation", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getReservations(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowableFields = new ArrayList<>(Arrays.asList(
            "createdOn", "modifiedOn", "user.id", "vehicle.id", "vehicle.registrationNumber", "pickupLocation.id",
            "dropoffLocation.id", "pickupDate", "dropoffDate", "status.id"
        ));

        Page<EReservation> reservations = sReservation.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched reservations", reservations, 
                ReservationDTO.class, EReservation.class));
    }

    @GetMapping(path = "/reservation/{reservationId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getReservation(@PathVariable Integer reservationId) {

        Optional<EReservation> reservationOpt = sReservation.getById(reservationId);
        if (!reservationOpt.isPresent()) {
            throw new NotFoundException("reservation with specified id not found", "reservationId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched reservation", new ReservationDTO(reservationOpt.get())));
    }

    @PatchMapping(path = "/reservation/{reservationId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateReservation(@PathVariable Integer reservationId, @RequestBody ReservationDTO reservationDTO) {

        Optional<EReservation> reservationOpt = sReservation.getById(reservationId);
        if (!reservationOpt.isPresent()) {
            throw new NotFoundException("reservation with specified id not found", "reservationId");
        }

        EReservation reservation = sReservation.update(reservationOpt.get(), reservationDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated reservation", new ReservationDTO(reservation)));
    }
}
