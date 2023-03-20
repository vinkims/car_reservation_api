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

@Entity(name = "user_civil_identities")
@Data
@NoArgsConstructor
public class EUserCivilIdentity implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "civil_identity_type_id", referencedColumnName = "id")
    private ECivilIdentityType civilIdentityType;

    @Id
    @Column(name = "civil_identity_value")
    private String civilIdentityValue;

    @Column(name = "created_on")
    private LocalDateTime createdOn;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private EUser user;
}
