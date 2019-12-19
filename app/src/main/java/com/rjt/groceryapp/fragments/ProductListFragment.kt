package com.rjt.groceryapp.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.rjt.groceryapp.R
import com.rjt.groceryapp.activities.app.Endpoints
import com.rjt.groceryapp.adapters.AdapterProducts
import com.rjt.groceryapp.models.Product
import com.rjt.groceryapp.models.ProductList
import kotlinx.android.synthetic.main.fragment_product.view.*

private const val ARG_PARAM1 = "param1"

class ProductListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int = 0
    private var listener: OnFragmentInteractionListener? = null

    lateinit var adapter: AdapterProducts
    var productList: ArrayList<Product> = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_product, container, false)


        view.recycler_view.layoutManager = LinearLayoutManager(this.activity!!)

        getProduct()
//        adapter = AdapterProducts(this.activity!!, productList, param1)
        adapter = AdapterProducts(this.activity!!, productList)
        view.recycler_view.adapter = adapter


        //view.text_view.text = param1
        return view
    }


    private fun getProduct() {


//        var tempProduct = ArrayList<Product>()
//
//        tempProduct.add(
//            Product(
//                "sub1",
//                "prod1",
//                "https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwj5r5TKhJbmAhVOPq0KHZGqDboQjRx6BAgBEAQ&url=https%3A%2F%2Fwww.sprouts.com%2Fdepartments%2Fbakery%2F&psig=AOvVaw3W5kjzPG7s8HJDGWjfqs7f&ust=1575344190760224",
//                1.23,
//                "desc1"
//
//            )
//
//        )
//        adapter.setData(tempProduct)


        val url = Endpoints.getProduct(param1)

        var filteredData = ArrayList<Product>()

        var requestQueue: RequestQueue = Volley.newRequestQueue(this.activity!!)

        var stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener { response ->
//
                var data = response.toString()
                var gson = GsonBuilder().create()
                var productList: ProductList = gson.fromJson(data, ProductList::class.java)


//                for((index,product) in productList.data.withIndex()) {
//                    if(productList.data.get(index).subId == param1) {
//                        filteredData.add(product)
//                    }
//                }


//                adapter.setData(productList.data, param1)
                Log.d("sungjin", "" +productList.data.size)
                adapter.setData(productList.data)


            },
            Response.ErrorListener {
                var tempProduct = ArrayList<Product>()

                tempProduct.add(
                    Product(
                        "sub1",
                        "1",
                        1.2,
                        "https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwj5r5TKhJbmAhVOPq0KHZGqDboQjRx6BAgBEAQ&url=https%3A%2F%2Fwww.sprouts.com%2Fdepartments%2Fbakery%2F&psig=AOvVaw3W5kjzPG7s8HJDGWjfqs7f&ust=1575344190760224",
                        1,
                        "desc1"



                    )

                )
                adapter.setData(tempProduct)

            })

        requestQueue.add(stringRequest)

    }


//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Int) =
            ProductListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}
