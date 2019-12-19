package com.rjt.groceryapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.rjt.groceryapp.R
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)



        btn_next.setOnClickListener {

            if (radioCash.isChecked == true){

                var sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE)
                var editor =sharedPreferences.edit()

                var name = edit_text_name_for_shipping.text.toString()

                editor.putString("NAME", name)
                editor.commit()

                var intent = Intent(this, ConfirmActivity::class.java)
                intent.putExtra("name", name)
                startActivity(intent)
            }
            else {

                var sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE)
                var editor =sharedPreferences.edit()

                var name = edit_text_name_for_shipping.text.toString()

                editor.putString("NAME", name)
                editor.commit()

                var intent = Intent(this, PaymentActivity::class.java)
                intent.putExtra("name", name)
                startActivity(intent)
            }

        }

    }


}
