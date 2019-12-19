package com.rjt.groceryapp.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.rjt.groceryapp.R
import com.rjt.groceryapp.activities.CartActivity
import com.rjt.groceryapp.activities.Database.DBHelper
import com.rjt.groceryapp.activities.Helper.ClickListener
import com.rjt.groceryapp.activities.app.Config
import com.rjt.groceryapp.models.Cart
import com.rjt.groceryapp.models.Category
import com.rjt.groceryapp.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cart.view.*
import kotlinx.android.synthetic.main.row_cart_adapter.view.*

class AdapterCart(val mContext: Context, var mList: ArrayList<Cart>, var clickListener: ClickListener) : RecyclerView.Adapter<AdapterCart.ViewHolder>(){
    var dbHelper = DBHelper(mContext)


    fun setData(list: ArrayList<Cart>){
        mList  = list;
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_cart_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: AdapterCart.ViewHolder, position: Int) {
        var cart = mList.get(position)
        holder.bindView(cart, position)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(cart: Cart, position: Int) {

            var totalPrice = 0.0
            totalPrice += cart.price * cart.qty

            itemView.text_view_product_name_cart.text = cart.productName
            itemView.text_view_price_cart.text = "$ %.2f".format(totalPrice)
            itemView.text_view_quantity.text = cart.qty.toString()

            Picasso.with(mContext)
                .load(Config.IMAGE_URL + cart.image)
                .into(itemView.image_view_cart)


            itemView.image_view_delete_cart.setOnClickListener {
                var id = cart.cartId.toString()

                mList.removeAt(position)
                dbHelper.deleteProduct(cart)
                notifyItemRemoved(position)
                notifyDataSetChanged()
            }


            itemView.image_view_plus.setOnClickListener {
                cart.qty = cart.qty + 1
                mList.set(position, cart)
                notifyItemChanged(position)
                dbHelper.updateProductQty(cart)

                clickListener.onQuantityChange()
//                var total = dbHelper.getTotalPrice()
//                var intent = Intent(mContext, CartActivity::class.java)
//                intent.putExtra("total", total)
            }

            itemView.image_view_minus.setOnClickListener {
                cart.qty = cart.qty - 1
                if(cart.qty > 0){
                    dbHelper.updateProductQty(cart)
                    mList.set(position, cart)
                    notifyItemChanged(position)

                    clickListener.onQuantityChange()
                }

                else {
                    dbHelper.deleteProduct(cart)
                    mList.removeAt(position)
                    notifyItemRemoved(position)
                    notifyDataSetChanged()

                    clickListener.onQuantityChange()
                }
            }

        }
    }


}