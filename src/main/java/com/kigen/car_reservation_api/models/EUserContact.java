package com.kigen.car_reservation_api.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "user_contacts")
@Data
@NoArgsConstructor
public class EUserContact implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_type_id", referencedColumnName = "id")
    private EContactType contactType;

    @Id
    @Column(name = "contact_value")
    private String contactValue;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private EUser user;
}
