package com.pr.purrfectrecipes.User

enum class CustomerStatus(val text:String) {
    UNVERIFIED("UNVERIFIED"),
    VERIFIED("VERIFIED"),
    PREMIUM("PREMIUM"),
    ADMIN("ADMIN"),
    MODERATOR("MODERATOR")
}