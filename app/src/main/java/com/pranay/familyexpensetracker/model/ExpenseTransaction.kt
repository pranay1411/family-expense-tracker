package com.pranay.familyexpensetracker.model

data class ExpenseTransaction(val id:String, val amount:Double, val merchant:String, val sender:String, val messageDate:Long, val rawMessage:String)
