package com.rjt.groceryapp.models

data class Cart (
    var cartId: Int = 0,
    var productName: String = "",
    var image: String = "",
    var qty: Int = 0,
    var price: Double = 0.00,
    var pid: String = ""

)