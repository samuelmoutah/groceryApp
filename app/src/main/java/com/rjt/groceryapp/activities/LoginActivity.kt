package com.rjt.groceryapp.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.rjt.groceryapp.R
import com.rjt.groceryapp.activities.Helper.SessionManager
import com.rjt.groceryapp.activities.app.Config
import com.rjt.groceryapp.activities.app.Endpoints
import com.rjt.groceryapp.models.Login
import com.rjt.groceryapp.models.User1
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val toolbar = toolbar as Toolbar
        toolbar.title = "LOG IN"
        setSupportActionBar(toolbar)


        button_login_submit.setOnClickListener{
            var isValidEmail = edit_text_login_email.nonEmpty()
            var isValidPassword = edit_text_login_password.nonEmpty()

            if(isValidEmail == false){
                edit_text_login_email.setHint("Please enter email")
                edit_text_login_email.setHintTextColor(Color.RED)
            }

            if(isValidPassword == false){
                edit_text_login_password.setHint("Please enter password")
                edit_text_login_password.setHintTextColor(Color.RED)
            }
            else {
                doLogin()
            }

        }

        text_view_new_user.setOnClickListener {
            var intent = Intent(this, RegisterActivity()::class.java)
            startActivity(intent)
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.login_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_signup -> {
                startActivity(Intent(this, RegisterActivity::class.java))
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

    private fun doLogin() {

        //var sharedPreferences: SharedPreferences = getSharedPreferences("Shared", Context.MODE_PRIVATE)

        val url: String = Endpoints.getLogin()
        var requestQueue = Volley.newRequestQueue(this)

        var email= edit_text_login_email.text.toString()
        var password = edit_text_login_password.text.toString()

        var credential_validate = User1(email, password)
        //var register = Registration("sam2", "sam2", "sam2555@gmail.com", "12345", "123456")
        var gson = Gson()
        //send user information to api
        val json = gson.toJson(credential_validate)

        val jsonObjReq = JsonObjectRequest(
            Request.Method.POST, url, JSONObject(json),
            Response.Listener {jsonObject: JSONObject ->

                //receive successful response jsonObject from api
                val validCredential = gson.fromJson(jsonObject.toString(), Login::class.java)
//
//                Toast.makeText(
//                    this, """${validCredential.token}
//                    ${validCredential.user}
//                """.trimMargin(), Toast.LENGTH_LONG
//                ).show()


                var sessionManager = SessionManager(this)
////
                sessionManager.createUser(validCredential.token, validCredential.user)


                var intent = Intent(this, CategoryActivity()::class.java)
                startActivity(intent)
//                //Toast.makeText(applicationContext, "logged in $jsonObject", Toast.LENGTH_SHORT).show()
//                Toast.makeText(applicationContext, "logged in" , Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, "failed $it", Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(jsonObjReq)
//        Toast.makeText(applicationContext, "loggen in  ", Toast.LENGTH_SHORT).show()

    }
}
