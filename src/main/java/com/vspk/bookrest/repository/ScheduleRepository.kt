package com.vspk.bookrest.repository

import com.vspk.bookrest.domain.Schedule
import org.springframework.data.jpa.repository.JpaRepository

interface ScheduleRepository: JpaRepository<Schedule, Long> {

}