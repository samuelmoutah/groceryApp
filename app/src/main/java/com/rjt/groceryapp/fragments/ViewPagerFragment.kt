package com.rjt.groceryapp.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.rjt.groceryapp.R

/**
 * A simple [Fragment] subclass.
 */
class ViewPagerFragment(private val mcontext: Context) : PagerAdapter() {
    private val image_resource =
        intArrayOf(

            R.drawable.cool,
            R.drawable.milk
        )
    private var layoutInflater: LayoutInflater? = null

    override fun getCount(): Int {
        return image_resource.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        layoutInflater = mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        val item_view = layoutInflater!!.inflate(R.layout.fragment_common_viewpager, container, false)

        val imageview = item_view.findViewById(R.id.image_view_viewpager) as ImageView
        imageview.setImageResource(image_resource[position])

        //val tvPosition = item_view.findViewById(R.id.tv_position) as TextView
        //tvPosition.text = "" + position

        container.addView(item_view)
        return item_view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }


}
