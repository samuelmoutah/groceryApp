package com.rjt.groceryapp.models

import java.io.Serializable

class Product (
    val subName: String,
    val productName: String,
    val image: String,
    val price: Double,
    val description: String
) : Serializable
{
    companion object{
        val PRODUCT: String = "product"
    }
}

