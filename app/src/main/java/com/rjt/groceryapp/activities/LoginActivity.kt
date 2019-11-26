package com.rjt.groceryapp.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.rjt.groceryapp.R
import com.rjt.groceryapp.models.Login
import com.rjt.groceryapp.models.Registration
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val toolbar = toolbar
        toolbar.title = "LOG IN"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        button_login_submit.setOnClickListener{
            doLogin()
            var intent = Intent(this, CategoryActivity()::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.login_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_setting -> {
                return true
            }
            android.R.id.home -> {

                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun doLogin() {

        var sharedPreferences: SharedPreferences = getSharedPreferences("Shared", Context.MODE_PRIVATE)

        val url: String = "https://apolis-grocery.herokuapp.com/api/auth/login"
        var requestQueue = Volley.newRequestQueue(this)

        var email= edit_text_login_email.text.toString()
        var password = edit_text_login_password.text.toString()

        var login = Login(email, password)
        //var register = Registration("sam2", "sam2", "sam2555@gmail.com", "12345", "123456")
        var gson = Gson()
        val json = gson.toJson(login)
        val jsonObjReq = JsonObjectRequest(
            Request.Method.POST, url, JSONObject(json),
            Response.Listener {jsonObject: JSONObject ->
                sharedPreferences.edit().putBoolean("Login", true).commit()

                //Toast.makeText(applicationContext, "logged in $jsonObject", Toast.LENGTH_SHORT).show()
                Toast.makeText(applicationContext, "logged in" , Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, "failed $it", Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(jsonObjReq)
//        Toast.makeText(applicationContext, "loggen in  ", Toast.LENGTH_SHORT).show()

    }
}
