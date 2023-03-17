package com.kigen.car_reservation_api.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.kigen.car_reservation_api.dtos.general.SearchCriteriaDTO;

public class SpecBuilder<T> {
    
    private List<SearchCriteriaDTO> searchParams;

    public SpecBuilder() {
        searchParams = new ArrayList<>();
    }

    public SpecBuilder<T> with(String key, String operation, Object value) {
        searchParams.add(new SearchCriteriaDTO(key, operation, value));
        return this;
    }

    public Specification<T> build() {
        if (searchParams.size() == 0) {
            return null;
        }

        Specification<T> result = null;

        for (SearchCriteriaDTO criteria : searchParams) {
            if (searchParams.indexOf(criteria) == 0) {
                result = new SearchSpec<T>(criteria);
                continue;
            }
            result = Specification.where(result).and(new SearchSpec<T>(criteria));
        }

        return result;
    }
}
