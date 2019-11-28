package com.rjt.groceryapp.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder

import com.rjt.groceryapp.R
import com.rjt.groceryapp.models.Product
import kotlinx.android.synthetic.main.fragment_product.view.*

private const val ARG_PARAM1 = "param1"
class ProductFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_product, container, false)
        view.text_view.text = param1
        return view
    }


    private fun getProuct(): ArrayList<Product> {
        
        var productList: ArrayList<Product> = ArrayList<Product>()
        val url = "http://rjtmobile.com/grocery/products.json"
        
        var requestQueue: RequestQueue = Volley.newRequestQueue(this.activity!!)
        
        var stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener { response ->
                var gson = GsonBuilder().create()
                val data = response.toString()

            },
            Response.ErrorListener {
                Toast.makeText(this.activity!!, it.message, Toast.LENGTH_SHORT).show()
            })

        requestQueue.add(stringRequest)

        return productList
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
        fun newInstance(param1: String) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}
