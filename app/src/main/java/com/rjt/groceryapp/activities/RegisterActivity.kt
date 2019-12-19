package com.rjt.groceryapp.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.rjt.groceryapp.R
import com.rjt.groceryapp.activities.Helper.SessionManager
import com.rjt.groceryapp.activities.app.Endpoints
import com.rjt.groceryapp.models.Registration
import com.wajahatkarim3.easyvalidation.core.view_ktx.atleastOneUpperCase
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        val toolbar = toolbar as Toolbar
        toolbar.title = "REGISTRATION"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //doRegister()
        button_submit.setOnClickListener{
            doRegister()

            var isValidFirstName = edit_text_firstname.nonEmpty()
            var isValidLasttName = edit_text_lastname.nonEmpty()
            var isValidEmail = edit_text_email.nonEmpty()
            var isValidMobile = edit_text_mobile.nonEmpty()
            //var isValidPassword = edit_text_password.atleastOneUpperCase()

            if (isValidFirstName == false) {
                edit_text_firstname.setHint("Please enter your first name")
                edit_text_firstname.setHintTextColor(Color.RED)
            }

            if (isValidLasttName == false) {
                edit_text_lastname.setHint("Please enter your last name")
                edit_text_lastname.setHintTextColor(Color.RED)
            }
            if (isValidEmail == false) {
                edit_text_email.setHint("Please enter email")
                edit_text_email.setHintTextColor(Color.RED)
            }
            if (isValidMobile == false) {
                edit_text_mobile.setHint("Please enter your phone number")
                edit_text_mobile.setHintTextColor(Color.RED)
            }

//            if (isValidPassword == false) {
//                edit_text_password.setHint("Invalid Password")
//                edit_text_password.setHintTextColor(Color.RED)
//            }


            else{
                var intent = Intent(this, LoginActivity()::class.java)
                startActivity(intent)
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.signup_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_login -> {
                var intent = Intent(this, LoginActivity()::class.java)
                startActivity(intent)
                return true
            }
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




    private fun doRegister() {

        val url: String = Endpoints.getRegister()
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
                //Toast.makeText(applicationContext, "registered", Toast.LENGTH_SHORT).show()
                Snackbar.make(root_layout, "Success!!, Welcome.", Snackbar.LENGTH_SHORT).show()
                Log.d("RegisterActivity", "Success: $it")
            },
            Response.ErrorListener {
                //Toast.makeText(applicationContext, "fail to register", Toast.LENGTH_SHORT).show()
                Snackbar.make(root_layout, "Fail to Register", Snackbar.LENGTH_SHORT).show()
                Log.e("RegisterActivity", "Fail: ${it.message}")
            }
        )
        requestQueue.add(jsonObjReq)

    }
}
