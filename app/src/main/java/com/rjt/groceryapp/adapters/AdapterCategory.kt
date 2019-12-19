package com.rjt.groceryapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rjt.groceryapp.R
import com.rjt.groceryapp.activities.SubCategoryActivity
import com.rjt.groceryapp.models.Category
import kotlinx.android.synthetic.main.row_category_adapter.view.*

class AdapterCategory (val mContext: Context, var mList: ArrayList<Category>) : RecyclerView.Adapter<AdapterCategory.ViewHolder>() {

    fun setData(list: ArrayList<Category>){
        mList  = list;
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_category_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    // From this point down, it's process the list one by one
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = mList.get(position)
        holder.bindView(category)

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(category: Category){
            itemView.text_view_category_name.text = category.catName
            itemView.image_view.setImageResource(
                when(category.catName){
                    "Bakery" -> {
                        R.drawable.bakery
                    }
                    "Dairy" -> {
                        R.drawable.dairy
                    }
                    "Beverages" -> {
                        R.drawable.beverage
                    }
                    else -> {
                        R.drawable.fruit
                    }
                }
            )



            itemView.setOnClickListener {
                var intent = Intent(mContext, SubCategoryActivity::class.java)
                intent.putExtra("CatId", category.catId)
                mContext.startActivity(intent)

            }
        }
    }

}