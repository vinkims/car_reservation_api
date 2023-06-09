package com.kigen.car_reservation_api.models.vehicle;

import java.io.Serializable;
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

@Entity(name = "vehicle_reviews")
@Data
@NoArgsConstructor
public class EVehicleReview implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private Integer id;

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    @Column(name = "review")
    private Integer review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private EStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private EUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private EVehicle vehicle;
}
