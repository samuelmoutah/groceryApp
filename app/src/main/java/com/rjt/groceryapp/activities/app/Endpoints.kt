package com.rjt.groceryapp.activities.app

class Endpoints {
    companion object {
        private val URL_CATERGORY: String = "category"
        private val URL_SUB_CATERGORY: String = "subcategory/"
        private val URL_PRODUCTS: String = "products/"
        private val URL_LOGIN: String = "auth/login"
        private val URL_REGISTER: String = "auth/register"

        fun getCatergory(): String {
            return Config.BASE_URL + URL_CATERGORY
        }

        fun getSubCatergory(catId: Int): String {
            return Config.BASE_URL + URL_SUB_CATERGORY + catId
        }

        fun getProduct(subId: Int): String {
            return Config.BASE_URL + URL_PRODUCTS + subId
        }

        fun getLogin(): String {
            return Config.BASE_URL + URL_LOGIN
        }

        fun getRegister(): String {
            return Config.BASE_URL + URL_REGISTER
        }
    }
}