package com.rjt.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.rjt.groceryapp.R

class SplashScreenActivity : AppCompatActivity() {


    lateinit var myHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        myHandler = Handler()

        myHandler.postDelayed({
            toNextActivity()
        }, 2000)
    }

    private fun toNextActivity() {
        startActivity(Intent(this, LoginActivity()::class.java))
    }
}
