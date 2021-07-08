package com.example.fashionecommercemobileapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.model.Bill
import java.text.NumberFormat
import java.util.*

class BillAdapter(context: Context, private val listBill: ArrayList<Bill>, val lang: String) :
    RecyclerView.Adapter<BillAdapter.ViewHolder>() {
    private var bill: MutableLiveData<Bill> = MutableLiveData(Bill())
    private var isClicked: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getBill(): MutableLiveData<Bill> = bill
    fun getIsClicked(): MutableLiveData<Boolean> = isClicked

    fun changeData(billList: List<Bill>) {
        this.listBill.apply {
            clear()
            addAll(billList)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var idBill: TextView = view.findViewById(R.id.textView_order_code)
        var date: TextView = view.findViewById(R.id.textView_order_date)
        var status: TextView = view.findViewById(R.id.textView_order_status)
        var total: TextView = view.findViewById(R.id.textView_order_price)
        var container: View = view.findViewById(R.id.order_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.idBill.text = listBill[position].id.toString()

        if (lang == "en")
        {
            holder.date.text = "Date: " + listBill[position].invoiceDate
            when (listBill[position].status) {
                0 -> holder.status.text = "Unconfirmed"
                1 -> holder.status.text = "Shipping"
                2 -> holder.status.text = "Done"
                3 -> holder.status.text = "Canceled"
            }
        } else
        {
            holder.date.text = "Ngày: " + listBill[position].invoiceDate
            when (listBill[position].status) {
                0 -> holder.status.text = "Chờ xác nhận"
                1 -> holder.status.text = "Đang giao"
                2 -> holder.status.text = "Đã giao"
                3 -> holder.status.text = "Đã hủy"
            }
        }

        holder.total.text =
            (Math.round(listBill[position].totalMoney!! *100)/100.0F).toString() + " $"
        holder.container.setOnClickListener {
            bill.value = Bill(
                listBill[position].id,
                listBill[position].idAccount,
                listBill[position].invoiceDate,
                listBill[position].status,
                listBill[position].idAddress,
                listBill[position].totalMoney,
                listBill[position].isRated
            )
            isClicked.value = true
        }
    }

    override fun getItemCount(): Int {
        return listBill.size
    }
}