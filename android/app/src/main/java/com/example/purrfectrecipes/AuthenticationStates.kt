package com.example.purrfectrecipes

enum class AuthenticationStates(val text:String)
{
    ADMIN("Admin"),
    MODERATOR("Moderator"),
    CUSTOMER("Customer"),
    FAILED_LOGIN("Failed_Login"),
    FAILED_REGISTER("Failed_Register")
}