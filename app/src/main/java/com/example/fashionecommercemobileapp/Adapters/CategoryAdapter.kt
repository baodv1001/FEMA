package com.example.fashionecommercemobileapp.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fashionecommercemobileapp.Model.Category
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.Views.MoreProductsActivity

class CategoryAdapter(
    private val context: Context,
    categoryList: List<Category>
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private val categoryList: List<Category> = categoryList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.category_recycler_items, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.categoryName.text = categoryList[position].name

        Glide.with(context).load(categoryList[position].image).into(holder.categoryImage)
        holder.itemView.setOnClickListener {
            val i = Intent(context, MoreProductsActivity::class.java)
            i.putExtra("name", categoryList[position].name)
            i.putExtra("image", categoryList[position].image)
            i.putExtra("idProductCode", categoryList[position].idProductCode)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class CategoryViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var categoryImage: ImageView = itemView.findViewById(R.id.category_image)
        var categoryName: TextView = itemView.findViewById(R.id.category_name)

    }

}