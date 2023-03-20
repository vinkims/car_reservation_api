package com.kigen.car_reservation_api.services.contact;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.car_reservation_api.dtos.contact.ContactTypeDTO;
import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.EContactType;
import com.kigen.car_reservation_api.repositories.ContactTypeDAO;
import com.kigen.car_reservation_api.specifications.SpecBuilder;
import com.kigen.car_reservation_api.specifications.SpecFactory;

@Service
public class SContactType implements IContactType {

    @Autowired
    private ContactTypeDAO contactTypeDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public Boolean checkExistsByName(String name) {
        return contactTypeDAO.existsByName(name);
    }

    @Override
    public EContactType create(ContactTypeDTO contactTypeDTO) {

        EContactType contactType = new EContactType();
        contactType.setCreatedOn(LocalDateTime.now());
        contactType.setDescription(contactTypeDTO.getDescription());
        contactType.setName(contactTypeDTO.getName());
        contactType.setRegexValue(contactTypeDTO.getRegexValue());

        save(contactType);
        return contactType;
    }

    @Override
    public Optional<EContactType> getById(Integer contactTypeId) {
        return contactTypeDAO.findById(contactTypeId);
    }

    @Override
    public EContactType getById(Integer contactTypeId, Boolean handleException) {
        Optional<EContactType> contactType = getById(contactTypeId);
        if (!contactType.isPresent() && handleException) {
            throw new InvalidInputException("contact type with specified id not found", "contactTypeId");
        }
        return contactType.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EContactType> getPaginatedList(PageDTO pageDTO, List<String> allowableFileds) {
        
        String search = pageDTO.getSearch();

        SpecBuilder<EContactType> specBuilder = new SpecBuilder<EContactType>();

        specBuilder = (SpecBuilder<EContactType>) specFactory.generateSpecification(search, specBuilder, allowableFileds);

        Specification<EContactType> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return contactTypeDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EContactType contactType) {
        contactTypeDAO.save(contactType);
    }

    @Override
    public EContactType update(EContactType contactType, ContactTypeDTO contactTypeDTO) throws IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        
        String[] fields = {"Name", "Description", "RegexValue"};
        for (String field : fields) {
            Method getField = ContactTypeDTO.class.getMethod(String.format("get%s", field));
            Object fieldValue = getField.invoke(contactTypeDTO);

            if (fieldValue != null) {
                fieldValue = fieldValue.getClass().equals(String.class) ? ((String) fieldValue).trim() : fieldValue;
                EContactType.class.getMethod("set" + field, fieldValue.getClass()).invoke(contactType, fieldValue);
            }
        }

        save(contactType);
        return contactType;
    }
    
}
