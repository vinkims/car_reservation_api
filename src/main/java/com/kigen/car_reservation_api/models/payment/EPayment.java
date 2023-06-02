package com.kigen.car_reservation_api.models.payment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.kigen.car_reservation_api.models.status.EStatus;
import com.kigen.car_reservation_api.models.user.EUser;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "payments")
@Data
@NoArgsConstructor
public class EPayment implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "description")
    private String description;

    @Column(name = "external_transaction_id")
    private String externalTransactionId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private Integer id;

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_channel_id", referencedColumnName = "id")
    private EPaymentChannel paymentChannel;

    @Column(name = "reference")
    private String reference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private EStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_type_id", referencedColumnName = "id")
    private ETransactionType transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private EUser user;
}
