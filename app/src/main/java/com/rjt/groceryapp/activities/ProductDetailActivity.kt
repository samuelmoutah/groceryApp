package com.rjt.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import com.rjt.groceryapp.R
import com.rjt.groceryapp.activities.Database.DBHelper
import com.rjt.groceryapp.activities.app.Config
import com.rjt.groceryapp.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.app_bar.*

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val product = intent.getSerializableExtra(Product.PRODUCT) as Product

        setUpToolBar(product)

        init(product)


//        button_add_cart.setOnClickListener {
//
//            var subName = "temp"
//            var subId = 1
//            var productName = text_view_product_name.text.toString()
//            var description = text_view_description.text.toString()
//            var price = text_view_product_price.text.toString()
//            var images = image_view.toString()
//
//            var dbHelper = DBHelper(this)
//
//            dbHelper.addProduct(Product(subName, subId, productName, images, price, description))
//
////            Toast.makeText(
////                this, """${validCredential.token}
////                    ${validCredential.user}
////                """.trimMargin(), Toast.LENGTH_LONG
////            ).show()
//            Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show()
//
//            Toast.makeText(this, "", Toast.LENGTH_SHORT).show()

    }

    private fun init(product: Product){

        text_view_product_name.text = product.productName
        text_view_description.text = product.description
        text_view_product_price.text = "$ " + product.price.toString()
        Picasso.with(this)
            .load(Config.IMAGE_URL + product.image)
            .into(image_view)

        button_add_cart.setOnClickListener {
            dbHelper = DBHelper(this)
            //product.qty = 1
            dbHelper.addProductQty(product, 1)

//            Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show()
            Snackbar.make(detail_layout, "Product added in the cart!!", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setUpToolBar(product: Product) {
        var toolbar = toolbar as Toolbar
        toolbar.title = product.productName
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                startActivity(Intent(this, CartActivity::class.java))
                return true
            }

            R.id.action_logout -> {
                startActivity(Intent(this, LoginActivity::class.java))
                return true
            }
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

}

