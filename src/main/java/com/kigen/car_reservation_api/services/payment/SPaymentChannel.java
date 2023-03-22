package com.kigen.car_reservation_api.services.payment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.car_reservation_api.dtos.general.PageDTO;
import com.kigen.car_reservation_api.dtos.payment.PaymentChannelDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.payment.EPaymentChannel;
import com.kigen.car_reservation_api.repositories.payment.PaymentChannelDAO;
import com.kigen.car_reservation_api.specifications.SpecBuilder;
import com.kigen.car_reservation_api.specifications.SpecFactory;

@Service
public class SPaymentChannel implements IPaymentChannel {

    @Autowired
    private PaymentChannelDAO paymentChannelDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public Boolean checkExistsByName(String name) {
        return paymentChannelDAO.existsByName(name);
    }

    @Override
    public EPaymentChannel create(PaymentChannelDTO paymentChannelDTO) {

        EPaymentChannel paymentChannel = new EPaymentChannel();
        paymentChannel.setCreatedOn(LocalDateTime.now());
        paymentChannel.setDescription(paymentChannelDTO.getDescription());
        paymentChannel.setName(paymentChannelDTO.getName());

        save(paymentChannel);
        return paymentChannel;
    }

    @Override
    public Optional<EPaymentChannel> getById(Integer paymentChannelId) {
        return paymentChannelDAO.findById(paymentChannelId);
    }

    @Override
    public EPaymentChannel getById(Integer paymentChannelId, Boolean handleException) {
        Optional<EPaymentChannel> paymentChannel = getById(paymentChannelId);
        if (paymentChannel.isPresent() && handleException) {
            throw new InvalidInputException("payment channel with specified id not found", "paymentChannelId");
        }
        return paymentChannel.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EPaymentChannel> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EPaymentChannel> specBuilder = new SpecBuilder<EPaymentChannel>();

        specBuilder = (SpecBuilder<EPaymentChannel>) specFactory.generateSpecification(search, specBuilder, allowableFields);

        Specification<EPaymentChannel> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return paymentChannelDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EPaymentChannel paymentChannel) {
        paymentChannelDAO.save(paymentChannel);
    }

    @Override
    public EPaymentChannel update(EPaymentChannel paymentChannel, PaymentChannelDTO paymentChannelDTO) {
        if (paymentChannelDTO.getDescription() != null) {
            paymentChannel.setDescription(paymentChannelDTO.getDescription());
        }
        if (paymentChannelDTO.getName() != null) {
            paymentChannel.setName(paymentChannelDTO.getName());
        }

        save(paymentChannel);
        return paymentChannel;
    }
    
}
