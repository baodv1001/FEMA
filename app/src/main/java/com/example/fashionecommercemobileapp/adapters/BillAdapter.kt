package com.example.fashionecommercemobileapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.model.Bill

class BillAdapter (context: Context, private val listBill: List<Bill>) : RecyclerView.Adapter<BillAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var orderCodeTextView: TextView = view.findViewById(R.id.order_code)
        var orderDateTextView: TextView = view.findViewById(R.id.order_date)
        var orderStatusTextView: TextView = view.findViewById(R.id.order_status)
        var orderTotalPrice: TextView = view.findViewById(R.id.order_total_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.orderCodeTextView.text = listBill[position].id.toString()
        holder.orderDateTextView.text = listBill[position].invoiceDate?.toLocaleString()
        holder.orderStatusTextView.text = listBill[position].status
        holder.orderTotalPrice.text = listBill[position].totalMoney.toString()
    }

    override fun getItemCount(): Int {
        return listBill.size
    }
}