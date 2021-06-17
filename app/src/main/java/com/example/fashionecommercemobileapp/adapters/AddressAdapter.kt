package com.example.fashionecommercemobileapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.model.Address


class AddressAdapter(
    context: Context,
    private val listAddress: ArrayList<Address>,
    private var isCheckOut: Boolean
) :
    RecyclerView.Adapter<AddressAdapter.ViewHolder>() {
    private var isSelected: MutableLiveData<Boolean> = MutableLiveData(false)
    private var address: MutableLiveData<Address> = MutableLiveData(Address(0,0, "", "", ""))

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.textView_name)
        var address: TextView = view.findViewById(R.id.textView_address)
        var phoneNumber: TextView = view.findViewById(R.id.textView_phoneNumber)
        var button_edit: View = view.findViewById(R.id.button_edit_address)
        var button_select: View = view.findViewById(R.id.address_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.address_recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = listAddress[position].name
        holder.address.text = listAddress[position].address
        holder.phoneNumber.text = listAddress[position].phoneNumber
        if (isCheckOut) {
            holder.button_edit.visibility = View.GONE
            holder.button_select.setOnClickListener {
                address.value = Address(
                    listAddress[position].idAddress,
                    listAddress[position].idAccount,
                    listAddress[position].name,
                    listAddress[position].address,
                    listAddress[position].phoneNumber
                )
                isSelected.value = true
            }
        }
    }

    override fun getItemCount() = listAddress.size

    fun changeData(addressList: List<Address>) {
        this.listAddress.apply {
            clear()
            addAll(addressList)
        }
        notifyDataSetChanged()
    }

    fun getSate() = isSelected
    fun getAddress() = address
}