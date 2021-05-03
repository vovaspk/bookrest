package com.vspk.bookrest.email;

public interface EmailService {
    void send(String emailTo, String subject, String message);
}
