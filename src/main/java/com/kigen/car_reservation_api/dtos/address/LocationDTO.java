package com.kigen.car_reservation_api.dtos.address;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.models.ELocation;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class LocationDTO {
    
    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private Integer id;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private LocalDateTime createdOn;

    private String name;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private AreaDTO area;

    private Integer areaId;

    private String additionalInfo;

    public LocationDTO(ELocation location) {
        setAdditionalInfo(location.getAdditionalInfo());
        setArea(new AreaDTO(location.getArea()));
        setCreatedOn(location.getCreatedOn());
        setId(location.getId());
        setName(location.getName());
    }
}
