package com.rjt.groceryapp.models

import java.io.Serializable

class Product (
//    val subName: String,
//    val subId: Int,

    val productName: String,
    val image: String,
    val price: Double,
    val description: String,
    var qty: Int = 0,
    val _id: String

) : Serializable
{
    companion object{
        val PRODUCT: String = "product"
    }
}

