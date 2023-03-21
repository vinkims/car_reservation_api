package com.kigen.car_reservation_api.controllers.address;

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

import com.kigen.car_reservation_api.dtos.address.AreaDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.exceptions.NotFoundException;
import com.kigen.car_reservation_api.models.address.EArea;
import com.kigen.car_reservation_api.responses.SuccessPaginatedResponse;
import com.kigen.car_reservation_api.responses.SuccessResponse;
import com.kigen.car_reservation_api.services.address.IArea;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Areas", description = "Area Endpoints")
public class CArea {
    
    @Autowired
    private IArea sArea;

    @PostMapping(path = "/area", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createArea(@RequestBody AreaDTO areaDTO) throws URISyntaxException {

        EArea area = sArea.create(areaDTO);

        return ResponseEntity
            .created(new URI("/municipality/" + area.getId()))
            .body(new SuccessResponse(201, "successfully created area", new AreaDTO(area)));
    }

    @GetMapping(path = "/area", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getAreasList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);
        if (pageDTO.getSortVal().equals("createdOn")) {
            pageDTO.setSortVal("id");
        }

        List<String> allowableFields = new ArrayList<>(Arrays.asList("municipality.id"));

        Page<EArea> areas = sArea.getPaginatedList(pageDTO, allowableFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched areas", areas, 
                AreaDTO.class, EArea.class));
    }
    
    @GetMapping(path = "/area/{areaId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getArea(@PathVariable Integer areaId) {

        Optional<EArea> areaOpt = sArea.getById(areaId);
        if (!areaOpt.isPresent()) {
            throw new NotFoundException("area with specified id not found", "areaId");
        }

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched area", new AreaDTO(areaOpt.get())));
    }

    @PatchMapping(path = "/area/{areaId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateArea(@PathVariable Integer areaId, @RequestBody AreaDTO areaDTO) {

        Optional<EArea> areaOpt = sArea.getById(areaId);
        if (!areaOpt.isPresent()) {
            throw new NotFoundException("area with specified id not found", "areaId");
        }

        EArea area = sArea.update(areaOpt.get(), areaDTO);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated area", new AreaDTO(area)));
    }
}
