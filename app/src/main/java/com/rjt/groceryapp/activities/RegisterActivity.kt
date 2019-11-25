package com.rjt.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.rjt.groceryapp.R
import com.rjt.groceryapp.models.Registration
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //doRegister()
        button_submit.setOnClickListener{
            doRegister()
            var intent = Intent(this, LoginActivity()::class.java)
            startActivity(intent)
        }

    }


    private fun doRegister() {
        val url: String = "https://apolis-grocery.herokuapp.com/api/auth/register"
        var requestQueue = Volley.newRequestQueue(this)

        var fname = edit_text_firstname.text.toString()
        var lname = edit_text_lastname.text.toString()
        var email= edit_text_email.text.toString()
        var mobile = edit_text_mobile.text.toString()
        var password = edit_text_password.text.toString()

        var register = Registration(fname, lname, email, mobile, password)
        //var register = Registration("sam2", "sam2", "sam646365@gmail.com", "12345", "123456")
        var gson = Gson()
        val json = gson.toJson(register)
        val jsonObjReq = JsonObjectRequest(
            Request.Method.POST, url, JSONObject(json),
            Response.Listener {
                Toast.makeText(applicationContext, "registered", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, "not create", Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(jsonObjReq)

    }
}
