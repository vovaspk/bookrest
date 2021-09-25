package com.vspk.bookrest.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "rest_thumbnails")
class RestaurantThumbnail(
    @OneToOne(mappedBy = "thumbnail")
    val restaurant: Restaurant,
    @Column(name = "path")
    val path: String
) : BaseEntity() {

}