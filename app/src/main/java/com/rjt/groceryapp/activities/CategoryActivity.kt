package com.rjt.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.rjt.groceryapp.R
import com.rjt.groceryapp.adapters.AdapterCategory
import com.rjt.groceryapp.models.Category
import com.rjt.groceryapp.models.CategoryList
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.fragment_recycler_view.view.*

class CategoryActivity : AppCompatActivity() {

    var adapter: AdapterCategory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        init()

        val toolbar = toolbar
        toolbar.title = "PRODUCT CATEGORY"
        setSupportActionBar(toolbar)

        getCategory()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.category_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_setting -> {
                return true
            }
            R.id.action_logout -> {
                startActivity(Intent(this, LoginActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun init() {

        var list = ArrayList<Category>()
        recycler_view.layoutManager = LinearLayoutManager(this)
        //view.recycler_view.layoutManager = GridLayoutManager(activity, 2)
        adapter = AdapterCategory(this, list)
        recycler_view.adapter = adapter


    }

    private fun getCategory() {
        val url: String = "https://apolis-grocery.herokuapp.com/api/category"
        var requestQueue = Volley.newRequestQueue(this)
        var stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                var data = response.toString()
                var gson = GsonBuilder().create()
                var categoryList: CategoryList = gson.fromJson(data, CategoryList::class.java)
//                Toast.makeText(activity, ""+ categoryList.data.size, Toast.LENGTH_SHORT).show()
//                Log.d("mo", data)



                adapter?.setData(categoryList.data)
                progress_bar.visibility = View.GONE
            },

            Response.ErrorListener {
                Log.e("mo", it.message)
            })
        requestQueue.add(stringRequest)
    }
}
