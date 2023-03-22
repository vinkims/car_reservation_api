package com.kigen.car_reservation_api.services.vehicle;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.dtos.vehicle.ReservationDTO;
import com.kigen.car_reservation_api.models.vehicle.EReservation;

public interface IReservation {
    
    EReservation create(ReservationDTO reservationDTO);

    Optional<EReservation> getById(Integer reservationId);

    EReservation getById(Integer reservationId, Boolean handleException);

    Page<EReservation> getPaginatedList(PageDTO pageDTO, List<String> allowableFields);

    void save(EReservation reservation);

    EReservation update(EReservation reservation, ReservationDTO reservationDTO);
}
