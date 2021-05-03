package com.vspk.bookrest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_verification")
public class Verification extends BaseEntity{
    @ManyToOne()
    @JoinColumn(name="user_id")
    private User user;
    @Column(name = "code")
    private String code;
    @Column(name = "expires")
    private Date expires_at;
    @Column(name = "confirmed")
    private Date confirmed_at;
}
