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
import com.kigen.car_reservation_api.dtos.vehicle.VehicleReviewDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.status.EStatus;
import com.kigen.car_reservation_api.models.user.EUser;
import com.kigen.car_reservation_api.models.vehicle.EVehicle;
import com.kigen.car_reservation_api.models.vehicle.EVehicleReview;
import com.kigen.car_reservation_api.repositories.vehicle.VehicleReviewDAO;
import com.kigen.car_reservation_api.services.status.IStatus;
import com.kigen.car_reservation_api.services.user.IUser;
import com.kigen.car_reservation_api.specifications.SpecBuilder;
import com.kigen.car_reservation_api.specifications.SpecFactory;

@Service
public class SVehicleReview implements IVehicleReview {

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private IUser sUser;

    @Autowired
    private IVehicle sVehicle;

    @Autowired
    private SpecFactory specFactory;

    @Autowired
    private VehicleReviewDAO vehicleReviewDAO;

    @Override
    public EVehicleReview create(VehicleReviewDTO vehicleReviewDTO) {

        EVehicleReview vehicleReview = new EVehicleReview();
        vehicleReview.setComment(vehicleReviewDTO.getComment());
        vehicleReview.setCreatedOn(LocalDateTime.now());
        vehicleReview.setReview(vehicleReviewDTO.getReview());
        
        Integer statusId = vehicleReviewDTO.getStatusId() == null ? activeStatusId : vehicleReviewDTO.getStatusId();
        setStatus(vehicleReview, statusId);

        setUser(vehicleReview, vehicleReviewDTO.getUserId());
        setVehicle(vehicleReview, vehicleReviewDTO.getVehicleId());

        save(vehicleReview);
        return vehicleReview;
    }

    @Override
    public Optional<EVehicleReview> getById(Integer vehicleReviewId) {
        return vehicleReviewDAO.findById(vehicleReviewId);
    }

    @Override
    public EVehicleReview getById(Integer vehicleReviewId, Boolean handleException) {
        Optional<EVehicleReview> vehicleReview = getById(vehicleReviewId);
        if (!vehicleReview.isPresent() && handleException) {
            throw new InvalidInputException("vehicle review with specified id not found", "vehiclReviewId");
        }
        return vehicleReview.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EVehicleReview> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {
        
        String search = pageDTO.getSearch();

        SpecBuilder<EVehicleReview> specBuilder = new SpecBuilder<EVehicleReview>();

        specBuilder = (SpecBuilder<EVehicleReview>) specFactory.generateSpecification(search, specBuilder, allowableFields);

        Specification<EVehicleReview> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return vehicleReviewDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EVehicleReview vehicleReview) {
        vehicleReviewDAO.save(vehicleReview);
    }

    public void setStatus(EVehicleReview vehicleReview, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        vehicleReview.setStatus(status);
    }

    public void setUser(EVehicleReview vehicleReview, Integer userId) {
        if (userId == null) { return; }

        EUser user = sUser.getById(userId, true);
        vehicleReview.setUser(user);
    }

    public void setVehicle(EVehicleReview vehicleReview, Integer vehicleId) {
        if (vehicleId == null) { return; }

        EVehicle vehicle = sVehicle.getById(vehicleId, true);
        vehicleReview.setVehicle(vehicle);
    }

    @Override
    public EVehicleReview update(EVehicleReview vehicleReview, VehicleReviewDTO vehicleReviewDTO) {
        if (vehicleReviewDTO.getComment() != null) {
            vehicleReview.setComment(vehicleReviewDTO.getComment());
        }
        if (vehicleReviewDTO.getReview() != null) {
            vehicleReview.setReview(vehicleReviewDTO.getReview());
        }
        vehicleReview.setModifiedOn(LocalDateTime.now());

        setStatus(vehicleReview, vehicleReviewDTO.getStatusId());
        setUser(vehicleReview, vehicleReviewDTO.getStatusId());
        setVehicle(vehicleReview, vehicleReviewDTO.getVehicleId());

        save(vehicleReview);
        return vehicleReview;
    }
    
}
