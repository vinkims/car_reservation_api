package com.kigen.car_reservation_api.services.address;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.kigen.car_reservation_api.dtos.address.LocationDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.models.ELocation;
import com.kigen.car_reservation_api.repositories.LocationDAO;

@Service
public class SLocation implements ILocation {

    @Autowired
    private LocationDAO locationDAO;

    @Override
    public ELocation create(LocationDTO locationDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Optional<ELocation> getById(Integer locationId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public ELocation getById(Integer locationId, Boolean handleException) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public Page<ELocation> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPaginatedList'");
    }

    @Override
    public void save(ELocation location) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    public void setArea(ELocation location, Integer areaId) {}

    @Override
    public ELocation update(ELocation location, LocationDTO locationDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    
}
