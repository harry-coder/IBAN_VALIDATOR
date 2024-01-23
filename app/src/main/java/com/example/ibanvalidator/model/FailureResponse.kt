package com.example.ibanvalidator.model

class FailureResponse {
    var code: Int
    var message: String
    var info: Any? = ""

    enum class Status {
        CONNECTION_FAILED, NO_INTERNET, UNKNOWN_ERROR, EMPTY_DATA, SESSION_EXPIRED
    }

    constructor(code: Int, message: String, info: Any) {
        this.code = code
        this.message = message
        this.info = info
    }
}

