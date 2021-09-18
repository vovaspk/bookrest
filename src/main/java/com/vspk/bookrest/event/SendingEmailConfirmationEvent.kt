package com.vspk.bookrest.event

import com.vspk.bookrest.domain.User
import org.springframework.context.ApplicationEvent

class SendingEmailConfirmationEvent(val registeredUser: User) : ApplicationEvent(
    registeredUser
)