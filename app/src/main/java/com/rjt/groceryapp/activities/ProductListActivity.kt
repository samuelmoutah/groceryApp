package com.rjt.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.rjt.groceryapp.R
import com.rjt.groceryapp.adapters.AdapterProducts
import com.rjt.groceryapp.models.Product
import com.rjt.groceryapp.models.ProductList
import kotlinx.android.synthetic.main.activity_product_list.*
import kotlinx.android.synthetic.main.app_bar.*

class ProductListActivity : AppCompatActivity() {
    //2.1
    lateinit var adapter: AdapterProducts
    //2.4
    var list: ArrayList<Product> = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        setUpToolBar()

        init()

    }

    private fun setUpToolBar() {
        var toolbar = toolbar
        toolbar.title = "Product List"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun init() {
        //2
        recycler_view.layoutManager = LinearLayoutManager(this)
        //2.2
        adapter = AdapterProducts(this, list)
        //2.5
        recycler_view.adapter = adapter

        //4
        getData()
    }

    //1
    private fun getData(){
        //1.1
        val url: String = "http://rjtmobile.com/grocery/products.json"
        //1.2
        var requestQueue = Volley.newRequestQueue(this)
        //1.3
        var stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener { responce ->
                //3
                val gson = GsonBuilder().create()
                //3.1
                val data: ProductList = gson.fromJson(responce.toString(), ProductList::class.java)
                //3.2
                adapter.setData(data.data)
            },
            Response.ErrorListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            })
        //5
        requestQueue.add(stringRequest)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {super.onOptionsItemSelected(item)}
        }
    }
}
