package com.example.fashionecommercemobileapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.model.CartInfo
import com.example.fashionecommercemobileapp.model.Product
import kotlinx.android.synthetic.main.cart_item.view.*


class CartAdapter(
    private val context: Context,
    private val listCart: ArrayList<CartInfo>,
    private val listProduct: ArrayList<Product>
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private var idDeletedProduct: MutableLiveData<Int> = MutableLiveData(0)
    private var isUpdated: MutableLiveData<Boolean> = MutableLiveData(false)
    private var idProduct: MutableLiveData<Int> = MutableLiveData(0)
    private var quantity: MutableLiveData<Int> = MutableLiveData(0)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageCart: ImageView = view.findViewById(R.id.imageView_product_cart)
        var productCart: TextView = view.findViewById(R.id.textView_product_cart)
        var infoCart: TextView = view.findViewById(R.id.textView_info_cart)
        var costCart: TextView = view.findViewById(R.id.textView_cost_cart)
        var quantityCart: TextView = view.findViewById(R.id.editText_quantity_cart)
    }

    fun getIdDeletedProduct(): MutableLiveData<Int> = idDeletedProduct
    fun getIsUpdated(): MutableLiveData<Boolean> = isUpdated
    fun getIdProduct(): MutableLiveData<Int> = idProduct
    fun getQuantity(): MutableLiveData<Int> = quantity

    fun changeData(cartList: List<CartInfo>, productList: List<Product>) {
        this.listCart.apply {
            clear()
            addAll(cartList)
        }
        this.listProduct.apply {
            clear()
            addAll(productList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (listCart.isEmpty() || listProduct.isEmpty()) {
            return
        }
        Glide.with(context)
            .load(listProduct[position].imageFile)
            .fitCenter()
            .into(holder.imageCart)

        holder.productCart.text = listProduct[position].name
        holder.infoCart.text = listProduct[position].unit
        holder.costCart.text = listProduct[position].price
        holder.quantityCart.text = listCart[position].quantity.toString()
        holder.itemView.editText_quantity_cart.setText(listCart[position].quantity.toString())

        holder.itemView.button_delete_cart.setOnClickListener {
            listCart.removeAt(position)
            idDeletedProduct.value = listProduct[position].idProduct?.toInt()
            notifyDataSetChanged()
        }

        holder.itemView.button_increase_cart.setOnClickListener {
            idProduct.value = listCart[position].idProduct
            listCart[position].quantity = listCart[position].quantity?.plus(1)
            quantity.value = listCart[position].quantity
            isUpdated.value = true
            notifyDataSetChanged()
        }

        holder.itemView.button_decrease_cart.setOnClickListener {
            idProduct.value = listCart[position].idProduct
            listCart[position].quantity = listCart[position].quantity?.minus(1)
            quantity.value = listCart[position].quantity
            isUpdated.value = true
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return listCart.size
    }
}