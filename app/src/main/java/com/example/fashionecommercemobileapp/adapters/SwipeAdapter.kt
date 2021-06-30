package com.example.fashionecommercemobileapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.fashionecommercemobileapp.R

class SwipeAdapter(ctx: Context) : PagerAdapter() {

    var imageResources: Array<String>
    = arrayOf<String>("https://images.unsplash.com/photo-1485125639709-a60c3a500bf1?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80",
        "https://images.unsplash.com/photo-1523381294911-8d3cead13475?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80",
        "https://images.unsplash.com/photo-1541377182189-74e4a4ea12e5?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1004&q=80",
        "https://images.unsplash.com/photo-1551488831-00ddcb6c6bd3?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80")
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
        //imageView.setImageResource(imageResources[position])
        Glide.with(context).load(imageResources[position]).into(imageView)
        container.addView(viewItem)
        return viewItem
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}