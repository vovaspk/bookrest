package com.vspk.bookrest.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToMany
import javax.persistence.Table
import javax.persistence.Transient
import lombok.Data

@Entity
@Table(name = "roles")
@JsonIgnoreProperties(value = ["created", "updated", "users", "status"])
class Role(
    @Column(name = "name")
    val name: String? = null,

    @Transient
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    val users: List<User>? = null
) : BaseEntity() {

//    @Column(name = "name")
//    val name: String? = null
//
//    @Transient
//    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
//    val users: List<User>? = null

    override fun toString(): String {
        return "Role{" +
                "id: " + super.id + ", " +
                "name: " + name + "}"
    }
}