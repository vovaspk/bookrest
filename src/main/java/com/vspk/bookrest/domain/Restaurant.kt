package com.vspk.bookrest.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table
import lombok.ToString

@Entity
@Table(name = "restaurant")
@ToString
class Restaurant(
    @Column(name="name")
    val name: String,
    @Column(name="email")
    val email: String? = null,
    @Column(name="phone")
    val phone: String,
    @OneToOne
    @JoinColumn(name = "thumbnail_id", referencedColumnName = "id")
    val thumbnail: RestaurantThumbnail,
    @Column(name="rating")
    val rating: Double,
    @Column(name="website")
    val website: String? = null,
    @Column(name="address")
    val address: String,
    @Column(name="description")
    val description: String? = null,
    @Column(name="cuisine_type")
    val cuisineType: String,
    @OneToOne
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    val schedule: Schedule,
    @OneToMany(mappedBy = "restaurant")
    var reviews: List<Review>? = null,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "rest_tags",
        joinColumns = [JoinColumn(name = "rest_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id", referencedColumnName = "id")]
    )
    var tags: List<Tag>? = null,
    @OneToMany(mappedBy = "restaurant")
    val photos: List<RestPhoto>
//TODO add in future pagination and sorting?
): BaseEntity() {
    fun isLiked(user:User):Boolean = user.likedRestaurants.contains(this)
}
