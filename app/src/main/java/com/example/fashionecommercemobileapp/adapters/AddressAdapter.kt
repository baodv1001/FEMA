package com.example.fashionecommercemobileapp.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionecommercemobileapp.model.Address
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.viewmodels.AddressViewModel
import com.example.fashionecommercemobileapp.views.EditAddressActivity
import kotlinx.android.synthetic.main.address_recycler_item.view.*

class AddressAdapter (private  val context: Context, private val listAddress: MutableList<Address>, private var addressViewModel: AddressViewModel?, private val idAccount: String) : RecyclerView.Adapter<AddressAdapter.ViewHolder>(){
 private var newAddress = listAddress
    class ViewHolder (view : View) : RecyclerView.ViewHolder(view){
        val recyclerView : androidx.recyclerview.widget.RecyclerView? = view.findViewById(R.id.address_recycler)
        var receiverNameTextView : TextView = view.findViewById(R.id.receiver_name)
        var receiverAddressTextView : TextView = view.findViewById(R.id.receiver_address)
        var receiverPhoneNumberTextView : TextView = view.findViewById(R.id.receiver_phone_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.address_recycler_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.receiverNameTextView.text = listAddress[position].name
        holder.receiverAddressTextView.text = listAddress[position].address
        holder.receiverPhoneNumberTextView.text = listAddress[position].phoneNumber

        holder.itemView.button_delete.setOnClickListener ( View.OnClickListener {
        addressViewModel!!.delAddressInfo(idAccount.toInt(),
                                        holder.receiverNameTextView.text.toString(),
                                        holder.receiverAddressTextView.text.toString(),
                                        holder.receiverPhoneNumberTextView.text.toString())

            newAddress.removeAt(position)
            notifyDataSetChanged()
        } )

        holder.itemView.button_edit.setOnClickListener  (View.OnClickListener {
            val intent = Intent(context, EditAddressActivity::class.java)
            intent.putExtra("name", newAddress[position].name)
            intent.putExtra("address", newAddress[position].address)
            intent.putExtra("phoneNumber", newAddress[position].phoneNumber)
            intent.putExtra("idAccount", idAccount.toInt())
            intent.putExtra("idAddress", newAddress[position].idAddress)

            (context as Activity).startActivityForResult(intent, 0)
            notifyDataSetChanged()
        })
    }

    override fun getItemCount() = listAddress.size
}