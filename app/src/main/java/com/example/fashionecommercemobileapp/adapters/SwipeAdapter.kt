package com.example.fashionecommercemobileapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.fashionecommercemobileapp.R

class SwipeAdapter(ctx: Context) : PagerAdapter() {

    var imageResources: Array<Int> = arrayOf<Int>(R.drawable.recommend, R.drawable.recommend)
    lateinit var layoutInflater: LayoutInflater
    var context: Context = ctx

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as ConstraintLayout
    }

    override fun getCount(): Int {
        return imageResources.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var viewItem: View = layoutInflater.inflate(R.layout.swipe_layout, container, false)
        var imageView: ImageView = viewItem.findViewById(R.id.image);
        imageView.setImageResource(imageResources[position])
        container.addView(viewItem)
        return viewItem
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}