package com.rjt.groceryapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.rjt.groceryapp.fragments.ProductFragment
import com.rjt.groceryapp.models.SubCategory

class AdapterFragment(fm: FragmentManager) : FragmentPagerAdapter(fm){

    var mTitleList : ArrayList<String> = ArrayList<String>()
    var mFragmentList : ArrayList<Fragment> = ArrayList<Fragment>()

    override fun getItem(position: Int): Fragment {
        return mFragmentList.get(position)
    }

    override fun getCount(): Int {
        return mTitleList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitleList.get(position)
    }

    fun addFragment(subCategory: SubCategory){
        mFragmentList.add(ProductFragment.newInstance(subCategory.subName))
        mTitleList.add(subCategory.subName)
    }

}