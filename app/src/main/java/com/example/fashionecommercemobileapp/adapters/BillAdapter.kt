package com.example.fashionecommercemobileapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.model.Bill

class BillAdapter(context: Context, private val listBill: List<Bill>) :
    RecyclerView.Adapter<BillAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var idBill: TextView = view.findViewById(R.id.textView_title_code)
        var date: TextView = view.findViewById(R.id.textView_order_date)
        var status: TextView = view.findViewById(R.id.textView_order_status)
        var total: TextView = view.findViewById(R.id.textView_order_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.idBill.text = listBill[position].id.toString()
        holder.date.text = listBill[position].invoiceDate
        holder.status.text = listBill[position].status.toString()
        holder.total.text = listBill[position].totalMoney.toString()
    }

    override fun getItemCount(): Int {
        return listBill.size
    }
}