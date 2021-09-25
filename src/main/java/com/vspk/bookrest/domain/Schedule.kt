package com.vspk.bookrest.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "schedule")
class Schedule(
    @OneToOne(mappedBy = "schedule")
    val restaurant: Restaurant,
    @Column(name = "moday")
    val monday:String,
    @Column(name = "tuesday")
    val tuesday:String,
    @Column(name = "wednesday")
    val wednesday:String,
    @Column(name = "thursday")
    val thursday:String,
    @Column(name = "friday")
    val friday:String,
    @Column(name = "saturday")
    val saturday:String,
    @Column(name = "sunday")
    val sunday:String
):BaseEntity() {

}
