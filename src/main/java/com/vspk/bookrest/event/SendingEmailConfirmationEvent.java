package com.vspk.bookrest.event;

import com.vspk.bookrest.domain.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SendingEmailConfirmationEvent extends ApplicationEvent {
    private final User registeredUser;

    public SendingEmailConfirmationEvent(User user) {
        super(user);
        this.registeredUser = user;
    }
}
