package com.kigen.car_reservation_api.dtos.address;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.models.EMunicipality;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class MunicipalityDTO {
    
    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private Integer id;

    private String name;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private RegionDTO region;

    private Integer regionId;

    public MunicipalityDTO(EMunicipality municipality) {
        setId(municipality.getId());
        setName(municipality.getName());
        setRegion(new RegionDTO(municipality.getRegion()));
    }
}
