package com.rjt.groceryapp.activities

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.rjt.groceryapp.R
import com.rjt.groceryapp.activities.Database.DBHelper
import com.rjt.groceryapp.activities.Helper.ClickListener
import com.rjt.groceryapp.adapters.AdapterCart
import com.rjt.groceryapp.models.Cart
import com.rjt.groceryapp.models.Product
import kotlinx.android.synthetic.main.activity_cart.*
//import kotlinx.android.synthetic.main.activity_cart.toolbarr
import kotlinx.android.synthetic.main.app_bar.*

class CartActivity : AppCompatActivity(), ClickListener {


    private lateinit var dbHelper: DBHelper
    private lateinit var adapter: AdapterCart

    private var mList: ArrayList<Cart> = ArrayList()
    private var total: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val toolbar = toolbarr as androidx.appcompat.widget.Toolbar
        toolbar.title = "Cart"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dbHelper = DBHelper(this)
        mList = dbHelper.getAllCartProduct()

        total = dbHelper.getTotalPrice()

        text_view_total.text = "$ " + total.toString()


        //"$ %.2f".format(total.toString())
//
//        var t = intent.getStringExtra("total").toString()
//
//        text_view_total.text = t

        //Toast.makeText(this, ""+mList.size, Toast.LENGTH_SHORT).show()

        recycler_view.layoutManager = LinearLayoutManager(this)
        adapter = AdapterCart(this, mList, this)
        recycler_view.adapter = adapter

//        init()

        btn_checkout.setOnClickListener {
            var intent = Intent(this, CheckoutActivity::class.java)
            startActivity(intent)
        }
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

    override fun onQuantityChange() {
        dbHelper = DBHelper(this)

        total = dbHelper.getTotalPrice()

        text_view_total.text = "$ " + total.toString()
    }

//    private fun init() {
//
//
//        var list = ArrayList<Cart>()
//        recycler_view.layoutManager = LinearLayoutManager(this)
//        adapter = AdapterCart(this, list)
//        recycler_view.adapter = adapter
//
//        dbHelper = DBHelper(this)
//        var data: Cursor = dbHelper.readAllProduct()
//
//        if (data.count == 0) {
//            Toast.makeText(this, "id does not match!!", Toast.LENGTH_SHORT).show()
//        }
//
//        while (data.moveToNext()) {
//            var id = data.getInt(0)
//            var prodName = data.getString(1)
//            var prodImage = data.getString(2)
//            var qty = data.getInt(3)
//            var price = data.getDouble(4)
//            var pid = data.getString(5)
//
//
//            //var bean: DataBean = DataBean(id, prodName, prodImage, qty, price, pid)
//            var bean: Cart = Cart(id, prodName, prodImage, qty, price, pid)
//            list.add(bean)
//
//
//        }
//
//        adapter?.setData(list)
//
//    }
}



