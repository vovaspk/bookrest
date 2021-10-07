package com.vspk.bookrest.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToMany

@Entity
class Tag(
//    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
//    val restaurants: List<Restaurant>? = null,//really need?
    @Column(name="text")
    val text: String
) : BaseEntity() {

}
