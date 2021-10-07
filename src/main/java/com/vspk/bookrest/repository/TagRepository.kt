package com.vspk.bookrest.repository

import com.vspk.bookrest.domain.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository : JpaRepository<Tag, Long>{

}