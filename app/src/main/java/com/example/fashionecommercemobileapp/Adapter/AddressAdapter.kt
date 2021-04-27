package com.example.fashionecommercemobileapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionecommercemobileapp.Models.Address
import com.example.fashionecommercemobileapp.Models.Popular
import com.example.fashionecommercemobileapp.R

class AddressAdapter (context: Context, private val listAddress: List<Address>) : RecyclerView.Adapter<AddressAdapter.ViewHolder>(){
    class ViewHolder (view : View) : RecyclerView.ViewHolder(view){
        val recyclerView : androidx.recyclerview.widget.RecyclerView? = view.findViewById(R.id.address_recycler)
        var receiverNameTextView : TextView = view.findViewById(R.id.receiver_name)
        var receiverAddressTextView : TextView = view.findViewById(R.id.receiver_address)
        var receiverPhoneNumberTextView : TextView = view.findViewById(R.id.receiver_phone_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.address_info, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.receiverNameTextView.text = listAddress[position].name
        holder.receiverAddressTextView.text = listAddress[position].address
        holder.receiverPhoneNumberTextView.text = listAddress[position].phoneNumber
    }

    override fun getItemCount() = listAddress.size
}