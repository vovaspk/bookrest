package com.vspk.bookrest.domain

import java.util.Date
import javax.persistence.Column
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import lombok.Data
import lombok.ToString
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate

@MappedSuperclass
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @CreatedDate
    @Column(name = "created")
    var created: Date? = null

    @LastModifiedDate
    @Column(name = "updated")
    var updated: Date? = null

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: Status = Status.NOT_ACTIVE

    @PrePersist
    protected fun onCreate() {
        created = Date()
        updated = Date()
    }

    @PreUpdate
    protected fun onUpdate() {
        updated = Date()
    }

    override fun toString(): String {
        return "BaseEntity(id=$id, created=$created, updated=$updated, status=$status)"
    }


}