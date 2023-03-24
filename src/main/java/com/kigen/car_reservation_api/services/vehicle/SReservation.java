package com.kigen.car_reservation_api.services.vehicle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.dtos.vehicle.ReservationDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.address.ELocation;
import com.kigen.car_reservation_api.models.status.EStatus;
import com.kigen.car_reservation_api.models.user.EUser;
import com.kigen.car_reservation_api.models.vehicle.EReservation;
import com.kigen.car_reservation_api.models.vehicle.EVehicle;
import com.kigen.car_reservation_api.repositories.vehicle.ReservationDAO;
import com.kigen.car_reservation_api.services.address.ILocation;
import com.kigen.car_reservation_api.services.status.IStatus;
import com.kigen.car_reservation_api.services.user.IUser;
import com.kigen.car_reservation_api.specifications.SpecBuilder;
import com.kigen.car_reservation_api.specifications.SpecFactory;

@Service
public class SReservation implements IReservation {

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Autowired
    private ILocation sLocation;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private IUser sUser;

    @Autowired
    private IVehicle sVehicle;

    @Autowired
    private ReservationDAO reservationDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public EReservation create(ReservationDTO reservationDTO) {

        EReservation reservation = new EReservation();
        reservation.setCreatedOn(LocalDateTime.now());
        reservation.setDropoffDate(reservationDTO.getDropoffDate());
        reservation.setPickupDate(reservationDTO.getPickupDate());
        
        setDropoffLocation(reservation, reservationDTO.getDropoffLocationId());
        setPickupLocation(reservation, reservationDTO.getPickupLocationId());

        Integer statusId = reservationDTO.getStatusId() == null ? activeStatusId : reservationDTO.getStatusId();
        setStatus(reservation, statusId);

        setUser(reservation, reservationDTO.getUserId());
        setVehicle(reservation, reservationDTO.getVehicleId());

        save(reservation);
        return reservation;
    }

    @Override
    public Optional<EReservation> getById(Integer reservationId) {
        return reservationDAO.findById(reservationId);
    }

    @Override
    public EReservation getById(Integer reservationId, Boolean handleException) {
        Optional<EReservation> reservation = getById(reservationId);
        if (!reservation.isPresent() && handleException) {
            throw new InvalidInputException("reservation with specified id not found", "reservationId");
        }
        return reservation.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EReservation> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EReservation> specBuilder = new SpecBuilder<EReservation>();

        specBuilder = (SpecBuilder<EReservation>) specFactory.generateSpecification(
            search, specBuilder, allowableFields);

        Specification<EReservation> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return reservationDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EReservation reservation) {
        reservationDAO.save(reservation);
    }

    public void setDropoffLocation(EReservation reservation, Integer locationId) {
        if (locationId == null) { return; }

        ELocation dropoffLocation = sLocation.getById(locationId, true);
        reservation.setDropoffLocation(dropoffLocation);
    }

    public void setPickupLocation(EReservation reservation, Integer pickupLocationId) {
        if (pickupLocationId == null) { return; }

        ELocation pickupLocation = sLocation.getById(pickupLocationId, true);
        reservation.setPickupLocation(pickupLocation);
    }

    public void setStatus(EReservation reservation, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        reservation.setStatus(status);
    }

    public void setUser(EReservation reservation, Integer userId) {
        if (userId == null) { return; }

        EUser user = sUser.getById(userId, true);
        reservation.setUser(user);
    }

    public void setVehicle(EReservation reservation, Integer vehicleId) {
        if (vehicleId == null) { return; }

        EVehicle vehicle = sVehicle.getById(vehicleId, true);
        reservation.setVehicle(vehicle);
    }

    @Override
    public EReservation update(EReservation reservation, ReservationDTO reservationDTO) {
        if (reservationDTO.getDropoffDate() != null) {
            reservation.setDropoffDate(reservationDTO.getDropoffDate());
        }
        if (reservationDTO.getPickupDate() != null) {
            reservation.setPickupDate(reservationDTO.getPickupDate());
        }
        reservation.setModifiedOn(LocalDateTime.now());
        setDropoffLocation(reservation, reservationDTO.getDropoffLocationId());
        setPickupLocation(reservation, reservationDTO.getPickupLocationId());
        setStatus(reservation, reservationDTO.getStatusId());
        setUser(reservation, reservationDTO.getUserId());
        setVehicle(reservation, reservationDTO.getVehicleId());

        save(reservation);
        return reservation;
    }
    
}
