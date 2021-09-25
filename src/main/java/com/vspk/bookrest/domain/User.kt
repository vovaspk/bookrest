package com.vspk.bookrest.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table
import lombok.Data
import lombok.ToString

@Entity
@Table(name = "users")
@ToString
class User(
    @Column(name = "username", unique = true)
    val username: String,

    @Column(name = "first_name")
    val firstName: String? = null,

    @Column(name = "last_name")
    val lastName: String? = null,

    @Column(name = "email")
    val email: String,

    @ToString.Exclude
    @JsonIgnore
    @Column(name = "password")
    val password: String,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    val roles: List<Role>,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "liked_restaurants",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "rest_id", referencedColumnName = "id")]
    )
    val likedRestaurants: List<Restaurant>,//is empty at start

    @Column(name = "verification_asked_times")
    var verificationTimesAsked: Int,

    ) : BaseEntity() {
    fun isVerified(): Boolean = status == Status.VERIFIED
    override fun toString(): String {
        return "User(id='$id', username='$username', firstName=$firstName, lastName=$lastName, email='$email', status='$status', roles=$roles, verificationTimesAsked=$verificationTimesAsked)"
    }

}



