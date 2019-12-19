package com.rjt.groceryapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.rjt.groceryapp.R
import com.rjt.groceryapp.activities.app.Endpoints
import com.rjt.groceryapp.adapters.AdapterCategory
import com.rjt.groceryapp.fragments.ViewPagerFragment
import com.rjt.groceryapp.models.Category
import com.rjt.groceryapp.models.CategoryList
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.app_bar.*

class CategoryActivity : AppCompatActivity() {

    lateinit var adapter: AdapterCategory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)


        var viewPagerFragment: ViewPagerFragment = ViewPagerFragment(this)
        view_pager1.adapter = viewPagerFragment

        init()

        val toolbar = toolbar as Toolbar
        toolbar.title = "PRODUCT CATEGORY"
        setSupportActionBar(toolbar)

        getCategory()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.category_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
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
        recycler_view.layoutManager = GridLayoutManager(this, 2)
        //view.recycler_view.layoutManager = GridLayoutManager(activity, 2)
        adapter = AdapterCategory(this, list)
        recycler_view.adapter = adapter


    }

    private fun getCategory() {
        val url: String = Endpoints.getCatergory()
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
//                var tempCategory = ArrayList<Category>()
//                tempCategory.add(
//                    Category(
//                        "1",
//                        "bakery",
//                        "https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwj5r5TKhJbmAhVOPq0KHZGqDboQjRx6BAgBEAQ&url=https%3A%2F%2Fwww.sprouts.com%2Fdepartments%2Fbakery%2F&psig=AOvVaw3W5kjzPG7s8HJDGWjfqs7f&ust=1575344190760224",
//                        "bakeryDesc",
//                        "slug",
//                        catId = 1
//                    )
//                )
//                adapter?.setData(tempCategory)
//                progress_bar.visibility = View.GONE
            })
        requestQueue.add(stringRequest)
    }
}
