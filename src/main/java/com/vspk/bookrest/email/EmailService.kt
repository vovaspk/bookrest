package com.vspk.bookrest.email

interface EmailService {
    fun send(emailTo: String, subject: String, message: String)
}