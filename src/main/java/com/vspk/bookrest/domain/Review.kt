package com.vspk.bookrest.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "review")
class Review(
    @Column(name = "text")
    val text: String? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id", nullable = false)
    val restaurant: Restaurant,
    @Column(name = "rating")
    val rating: Double,
    @Column(name = "likes_count")
    val likes: Int
) : BaseEntity() {

}
