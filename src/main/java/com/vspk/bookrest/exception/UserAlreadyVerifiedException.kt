package com.vspk.bookrest.exception

class UserAlreadyVerifiedException : RuntimeException {
    val username: String?
    val id: Long?
    override val message: String

    constructor(username: String?, message: String) {
        this.username = username
        this.message = message
        id = null
    }

    constructor(id: Long?, message: String) {
        this.id = id
        this.message = message
        username = null
    }
}