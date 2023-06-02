package com.kigen.car_reservation_api.services.payment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import com.kigen.car_reservation_api.dtos.payment.PaymentDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.payment.EPayment;
import com.kigen.car_reservation_api.models.payment.EPaymentChannel;
import com.kigen.car_reservation_api.models.payment.ETransactionType;
import com.kigen.car_reservation_api.models.status.EStatus;
import com.kigen.car_reservation_api.models.user.EUser;
import com.kigen.car_reservation_api.repositories.payment.PaymentDAO;
import com.kigen.car_reservation_api.services.status.IStatus;
import com.kigen.car_reservation_api.services.user.IUser;
import com.kigen.car_reservation_api.specifications.SpecBuilder;
import com.kigen.car_reservation_api.specifications.SpecFactory;

@Service
public class SPayment implements IPayment {

    @Value(value = "${default.value.status.complete-id}")
    private Integer completeStatusId;

    @Autowired
    private IPaymentChannel sPaymentChannel;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private ITransactionType sTransactionType;

    @Autowired
    private IUser sUser;

    @Autowired
    private PaymentDAO paymentDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public EPayment create(PaymentDTO paymentDTO) {

        EPayment payment = new EPayment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setCreatedOn(LocalDateTime.now());
        payment.setDescription(paymentDTO.getDescription());
        payment.setExternalTransactionId(paymentDTO.getExternalTransactionId());
        payment.setReference(paymentDTO.getReference());
        setPaymentChannel(payment, paymentDTO.getPaymentChannelId());
        
        Integer statusId = paymentDTO.getStatusId() == null ? completeStatusId : paymentDTO.getStatusId();
        setStatus(payment, statusId);

        setTransactionType(payment, paymentDTO.getTransactionTypeId());
        setUser(payment, paymentDTO.getUserId());

        save(payment);
        return payment;
    }

    @Override
    public Optional<EPayment> getByExternalTransactionId(String externalTransactionId) {
        return paymentDAO.findByExternalTransactionId(externalTransactionId);
    }

    @Override
    public Optional<EPayment> getById(Integer paymentId) {
        return paymentDAO.findById(paymentId);
    }

    @Override
    public EPayment getById(Integer paymentId, Boolean handleException) {
        Optional<EPayment> paymentOpt = getById(paymentId);
        if (!paymentOpt.isPresent() && handleException) {
            throw new InvalidInputException("payment with specified id not found", "paymentId");
        }
        return paymentOpt.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EPayment> getPaginatedList(PageDTO pageDTO, List<String> allowableFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EPayment> specBuilder = new SpecBuilder<EPayment>();

        specBuilder = (SpecBuilder<EPayment>) specFactory.generateSpecification(search, specBuilder, allowableFields);

        Specification<EPayment> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return paymentDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EPayment payment) {
        paymentDAO.save(payment);
    }

    public void setPaymentChannel(EPayment payment, Integer paymentChannelId) {
        if (paymentChannelId == null) { return; }

        EPaymentChannel paymentChannel = sPaymentChannel.getById(paymentChannelId, true);
        payment.setPaymentChannel(paymentChannel);
    }

    public void setStatus(EPayment payment, Integer statusId) {
        if (statusId == null) { return; }

        EStatus status = sStatus.getById(statusId, true);
        payment.setStatus(status);
    }

    public void setTransactionType(EPayment payment, Integer transactionTypeId) {
        if (transactionTypeId == null) { return; }

        ETransactionType transactionType = sTransactionType.getById(transactionTypeId, true);
        payment.setTransactionType(transactionType);
    }

    public void setUser(EPayment payment, Integer userId) {
        if (userId == null) { return; }

        EUser user = sUser.getById(userId, true);
        payment.setUser(user);
    }

    @Override
    public EPayment update(EPayment payment, PaymentDTO paymentDTO) throws IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        String[] fields = {"ExternalTransactionId", "Amount", "Descirption", "Reference"};
        for (String field : fields) {
            Method getField = PaymentDTO.class.getMethod(String.format("get%s", field));
            Object fieldValue = getField.invoke(paymentDTO);

            if (fieldValue != null) {
                fieldValue = fieldValue.getClass().equals(String.class) ? ((String) fieldValue).trim() : fieldValue;
                EPayment.class.getMethod("set" + field, fieldValue.getClass()).invoke(payment, fieldValue);
            }
        }

        payment.setModifiedOn(LocalDateTime.now());
        setPaymentChannel(payment, paymentDTO.getPaymentChannelId());
        setStatus(payment, paymentDTO.getStatusId());
        setTransactionType(payment, paymentDTO.getTransactionTypeId());
        setUser(payment, paymentDTO.getStatusId());

        save(payment);
        return payment;
    }
    
}
