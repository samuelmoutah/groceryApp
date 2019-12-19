package com.rjt.groceryapp.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rjt.groceryapp.R
import kotlinx.android.synthetic.main.activity_confirm.*

class ConfirmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

//        var sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE)
//        var name = sharedPreferences.getString("NANE", null)
        var name = intent.getStringExtra("name")

        text_view_name_confirm.setText(name)

    }
}
