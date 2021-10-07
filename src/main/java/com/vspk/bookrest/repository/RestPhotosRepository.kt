package com.vspk.bookrest.repository

import com.vspk.bookrest.domain.RestPhoto
import org.springframework.data.jpa.repository.JpaRepository

interface RestPhotosRepository: JpaRepository<RestPhoto, Long> {

}