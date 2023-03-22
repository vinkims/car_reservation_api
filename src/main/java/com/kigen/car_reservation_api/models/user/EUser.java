package com.kigen.car_reservation_api.models.user;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.kigen.car_reservation_api.models.status.EStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Data
@NoArgsConstructor
public class EUser implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Column(name = "age")
    private Integer age;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<EUserCivilIdentity> civilIdentities;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<EUserContact> contacts;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "first_name")
    private String firstName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private Integer id;

    @Column(name = "last_active_on")
    private LocalDateTime lastActiveOn;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    @Column(name = "passcode")
    private String passcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private ERole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private EStatus status;

    public String toString() {
        return String.format("EUser <id:%s, firstName:%s, lastName%s>", id, firstName, lastName);
    }

    public void setPasscode(String pass) {
        if (pass != null) {
            this.passcode = new BCryptPasswordEncoder().encode(pass);
        }
    }
}
