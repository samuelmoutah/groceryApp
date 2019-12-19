package com.rjt.groceryapp.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rjt.groceryapp.R
import com.rjt.groceryapp.activities.ProductDetailActivity
import com.rjt.groceryapp.activities.app.Config
import com.rjt.groceryapp.activities.app.Config.Companion.IMAGE_URL
import com.rjt.groceryapp.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_product_adpater.view.*

//class AdapterProducts (val mContext: Context, var mList: ArrayList<Product>, var sub: String?)

class AdapterProducts(val mContext: Context, var mList: ArrayList<Product>) :
    RecyclerView.Adapter<AdapterProducts.ViewHolder>() {

//    fun setData(list: ArrayList<Product>, param1: String?) {
//        mList = list
//        sub = param1
//        notifyDataSetChanged()
//    }

    fun setData(list: ArrayList<Product>) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.row_product_adpater, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = mList.get(position)
        holder.bindView(product)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(product: Product) {
//
                itemView.text_view_product_name.text = product.productName
                itemView.text_view_price.text = "$ " + product.price.toString()

                Picasso.with(mContext)
                    .load(Config.IMAGE_URL + product.image)
                    .into(itemView.image_view)
                //Log.d("sungjin", product.images)



            itemView.setOnClickListener {
                var intent = Intent(mContext, ProductDetailActivity::class.java)
                intent.putExtra(Product.PRODUCT, product)

                mContext.startActivity(intent)
            }
        }

    }
}