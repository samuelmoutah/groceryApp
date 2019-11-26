package com.rjt.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.rjt.groceryapp.R
import com.rjt.groceryapp.adapters.AdapterFragment
import com.rjt.groceryapp.models.CategoryList
import com.rjt.groceryapp.models.SubCategory
import com.rjt.groceryapp.models.SubCategoryList
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.app_bar.*

class SubCategoryActivity : AppCompatActivity() {

    var adapterFragment: AdapterFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)


        init()
        //getSubCategory()

        val toolbar = toolbar
        toolbar.title = "PRODUCT LIST"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sub_category_menu, menu)
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
            android.R.id.home -> {

                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun init() {

        adapterFragment = AdapterFragment(supportFragmentManager)

        for(subCategory in getSubCategory()){
                adapterFragment?.addFragment(subCategory)
            }
        view_pager.adapter = adapterFragment
        tab_layout.setupWithViewPager(view_pager)

    }

    private fun getSubCategory(): ArrayList<SubCategory> {

        var list: ArrayList<SubCategory> = ArrayList<SubCategory>()

        val categoryName = intent.getStringExtra("CatName")

        val url = "https://apolis-grocery.herokuapp.com/api/subcategory/name/$categoryName"

        var requestQueue = Volley.newRequestQueue(this)
        var stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                var data = response.toString()
                var gson = GsonBuilder().create()
                var subCategoryList: SubCategoryList = gson.fromJson(data, SubCategoryList::class.java)



                list = subCategoryList.data

                for(subCategory in list){
                    adapterFragment?.addFragment(subCategory)
                }
                view_pager.adapter = adapterFragment
                tab_layout.setupWithViewPager(view_pager)

                //adapterFragment?.notifyDataSetChanged()

            },

            Response.ErrorListener {
                Log.e("mo", it.message)
            })

        requestQueue.add(stringRequest)


//        list.add(SubCategory("1"))
//        list.add(SubCategory("2"))
//        list.add(SubCategory("3"))
        return list
    }
}
//var requestQueue = Volley.newRequestQueue(this)
//var stringRequest = StringRequest(
//    Request.Method.GET, url,
//    Response.Listener { response ->
//        var data = response.toString()
//        var gson = GsonBuilder().create()
//        var categoryList: CategoryList = gson.fromJson(data, CategoryList::class.java)
////                Toast.makeText(activity, ""+ categoryList.data.size, Toast.LENGTH_SHORT).show()
////                Log.d("mo", data)
//
//        adapter?.setData(categoryList.data)
//        progress_bar.visibility = View.GONE
//    },
//
//    Response.ErrorListener {
//        Log.e("mo", it.message)
//    })