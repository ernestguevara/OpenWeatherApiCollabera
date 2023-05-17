package com.ernestguevara.openweatherapicollabera.util

object Constants {
    const val DB_NAME = "weather_db"

    /*
    Login
     */
    const val ERROR_LOGIN = "Sorry, we couldn't find an account with the provided credentials. Please verify your username or email and try again"
    const val ERROR_SIGNUP = "Sorry, we were unable to create your account at the moment. Please try again later"
    const val ERROR_INPUTS = "Incorrect inputs please check!"

    /*
    API
     */
    const val ERROR_HTTP = "An error occurred while processing your request. Please check your network connection and try again later."
    const val ERROR_IO = "An error occurred while accessing the required data. Please check your internet connection and try again."
    const val ERROR_GENERIC = "Oops! Something went wrong. Please try again later."
}