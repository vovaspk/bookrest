package com.vspk.bookrest.domain

import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table


@Entity
@Table(name = "user_verification")
class Verification(
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @Column(name = "code")
    val code: String,

    @Column(name = "expires")
    val expires_at: Date,

    @Column(name = "confirmed")
    var confirmed_at: Date? = null
) : BaseEntity()