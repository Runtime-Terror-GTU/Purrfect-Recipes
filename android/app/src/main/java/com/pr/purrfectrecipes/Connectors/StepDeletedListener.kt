package com.pr.purrfectrecipes.Connectors

interface StepDeletedListener {
    fun onStepDeleted(stepText:String)
    fun onStepChanged(stepText:String, stepNo:Int)
}