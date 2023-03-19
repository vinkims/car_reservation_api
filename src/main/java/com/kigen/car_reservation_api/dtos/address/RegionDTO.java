package com.kigen.car_reservation_api.dtos.address;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.car_reservation_api.models.ERegion;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class RegionDTO {
    
    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private Integer id;

    private String name;

    @Schema(accessMode = AccessMode.READ_ONLY, hidden = true)
    private CountryDTO country;

    private Integer countryId;

    public RegionDTO(ERegion region) {
        setCountry(new CountryDTO(region.getCountry()));
        setId(region.getId());
        setName(region.getName());
    }
}
