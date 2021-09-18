package com.vspk.bookrest;

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
open class BookrestApplication

fun main(args: Array<String>) {
    runApplication<BookrestApplication>(*args)
}
