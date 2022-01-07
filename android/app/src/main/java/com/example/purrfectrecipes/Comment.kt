package com.example.purrfectrecipes

import com.example.purrfectrecipes.User.CustomerStatus

class Comment(id: String, ownerId:String, content:String) {
    private val commentId = id
    fun getId(): String {
        return commentId
    }

    private val ownerId = ownerId
    fun getOwnerId(): String {
        return ownerId
    }

    var ownerName:String?=null
    var ownerStatus:CustomerStatus?=null
    var ownerPicURL:String?=null

    private val commentContent = content
    fun getCommentContent(): String {
        return commentContent
    }
}