package com.kigen.car_reservation_api.services.role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.dtos.role.RoleDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.user.ERole;
import com.kigen.car_reservation_api.repositories.user.RoleDAO;
import com.kigen.car_reservation_api.specifications.SpecBuilder;
import com.kigen.car_reservation_api.specifications.SpecFactory;

@Service
public class SRole implements IRole {

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public Boolean checkExistsByName(String name) {
        return roleDAO.existsByName(name);
    }

    @Override
    public ERole create(RoleDTO roleDTO) {

        ERole role = new ERole();
        role.setCreatedOn(LocalDateTime.now());
        role.setDescription(roleDTO.getDescription());
        role.setName(roleDTO.getName());

        save(role);
        return role;
    }

    @Override
    @Caching(cacheable = {@Cacheable(cacheNames = "roles", unless = "#result == null")})
    public Optional<ERole> getById(Integer roleId) {
        return roleDAO.findById(roleId);
    }

    @Override
    public ERole getById(Integer roleId, Boolean handleException) {
        Optional<ERole> role = getById(roleId);
        if (!role.isPresent() && handleException) {
            throw new InvalidInputException("role with specified id not found", "roleId");
        }
        return role.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<ERole> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {
        
        String search = pageDTO.getSearch();

        SpecBuilder<ERole> specBuilder = new SpecBuilder<ERole>();

        specBuilder = (SpecBuilder<ERole>) specFactory.generateSpecification(search, specBuilder, allowableFields);

        Specification<ERole> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return roleDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(ERole role) {
        roleDAO.save(role);
    }

    @Override
    public ERole update(ERole role, RoleDTO roleDTO) {
        if (roleDTO.getDescription() != null) {
            role.setDescription(roleDTO.getDescription());
        }
        if (roleDTO.getName() != null) {
            role.setName(roleDTO.getName());
        }

        save(role);
        return role;
    }
    
}
