package com.ernestguevara.openweatherapicollabera.util

enum class RequestState(val message: String?) {
    Loading(""),
    Finished(""),
    Failed(""),
    NoInternetConnection(""),
    TokenExpired(""),
    Forbidden("")
}