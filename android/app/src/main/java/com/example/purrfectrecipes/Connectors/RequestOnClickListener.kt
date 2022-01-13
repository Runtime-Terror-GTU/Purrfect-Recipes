package com.example.purrfectrecipes.Connectors

interface RequestOnClickListener {
    fun onApproveClick(requestID:String)
    fun onDenyClick(requestID:String)
}