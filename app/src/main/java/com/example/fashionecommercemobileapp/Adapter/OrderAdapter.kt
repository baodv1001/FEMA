package com.example.fashionecommercemobileapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionecommercemobileapp.Models.Popular
import com.example.fashionecommercemobileapp.R
import java.security.AccessControlContext

class OrderAdapter (context: Context, private  val listOrder : List<Popular>) : RecyclerView.Adapter<OrderAdapter.ViewHolder>(){
    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val recyclerView : androidx.recyclerview.widget.RecyclerView? = view.findViewById(R.id.order_recycler)
        var orderCodeTextView : TextView = view.findViewById(R.id.order_code)
        var orderDateTextView : TextView = view.findViewById(R.id.order_date)
        var orderStatusTextView : TextView = view.findViewById(R.id.order_status)
        var orderAmountTextView : TextView = view.findViewById(R.id.order_amount)
        var orderPriceTextView : TextView = view.findViewById(R.id.order_total_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_info, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.orderCodeTextView.text = listOrder[position].deliveryCharges
        holder.orderDateTextView.text = listOrder[position].deliveryTime
        holder.orderStatusTextView.text = listOrder[position].note
        holder.orderAmountTextView.text = listOrder[position].rating
        holder.orderPriceTextView.text = listOrder[position].price
    }

    override fun getItemCount() = listOrder.size

}