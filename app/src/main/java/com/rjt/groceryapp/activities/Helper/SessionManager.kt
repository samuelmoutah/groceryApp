package com.rjt.groceryapp.activities.Helper

import android.content.Context
import android.content.SharedPreferences
import com.rjt.groceryapp.models.Login
import com.rjt.groceryapp.models.User2

class SessionManager(var context: Context) {

    var sharedPreferences: SharedPreferences
    var editor: SharedPreferences.Editor

    init {
        sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }


    companion object {
        val FILE_NAME: String = "myPref"
        val FIRST_NAME: String = "first_name"
        val LAST_NAME: String = "last_name"
        val EMAIL: String = "email"
        val MOBILE: String = "mobile"
        val PASSWORD: String = "password"
        val IS_LOGIN: String = "is_login"
        val Token: String = "token"
    }


    fun createUser(token: String, user: User2) {
        editor.putString(EMAIL, user.email)
        editor.putString(PASSWORD, user.password)
        editor.putString(MOBILE, user.mobile)
        editor.putString(Token, token)
        editor.putBoolean(IS_LOGIN, true)
        editor.commit()
    }

    //fun setLogin()

    fun isLogin(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGIN, false)
    }

    fun isLogout() {
        sharedPreferences.edit().clear()
    }

}