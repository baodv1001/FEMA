package com.example.fashionecommercemobileapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.model.CartInfo
import com.example.fashionecommercemobileapp.model.Color
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.model.Size
import kotlinx.android.synthetic.main.cart_item.view.*
import java.text.NumberFormat
import java.util.*


class CartAdapter(
    private val context: Context,
    private val listCart: ArrayList<CartInfo>,
    private val listProduct: ArrayList<Product>,
    private val listSize: ArrayList<Size>,
    private val listColor: ArrayList<Color>
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private var idDeletedProduct: MutableLiveData<Int> = MutableLiveData(0)
    private var isUpdated: MutableLiveData<Boolean> = MutableLiveData(false)
    private var idProduct: MutableLiveData<Int> = MutableLiveData(0)
    private var idSize: MutableLiveData<Int> = MutableLiveData(0)
    private var idColor: MutableLiveData<Int> = MutableLiveData(0)
    private var quantity: MutableLiveData<Int> = MutableLiveData(0)
    private var selectedProduct: MutableLiveData<Product> = MutableLiveData(Product())
    private var isSelected: MutableLiveData<Boolean> = MutableLiveData(false)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageCart: ImageView = view.findViewById(R.id.imageView_product_cart)
        var productCart: TextView = view.findViewById(R.id.textView_product_cart)
        var infoCart: TextView = view.findViewById(R.id.textView_info_cart)
        var costCart: TextView = view.findViewById(R.id.textView_cost_cart)
        var quantityCart: TextView = view.findViewById(R.id.editText_quantity_cart)
        var container: View = view.findViewById(R.id.cart_item)
    }

    fun getIdDeletedProduct(): MutableLiveData<Int> = idDeletedProduct
    fun getIsUpdated(): MutableLiveData<Boolean> = isUpdated
    fun getIdProduct(): MutableLiveData<Int> = idProduct
    fun getIdSize(): MutableLiveData<Int> = idSize
    fun getIdColor(): MutableLiveData<Int> = idColor
    fun getQuantity(): MutableLiveData<Int> = quantity
    fun getSelectedProduct(): MutableLiveData<Product> = selectedProduct
    fun getIsSelected(): MutableLiveData<Boolean> = isSelected

    fun changeData(
        cartList: List<CartInfo>,
        productList: List<Product>,
        sizeList: List<Size>,
        colorList: List<Color>
    ) {
        this.listCart.apply {
            clear()
            addAll(cartList)
        }
        this.listProduct.apply {
            clear()
            addAll(productList)
        }
        this.listSize.apply {
            clear()
            addAll(sizeList)
        }
        this.listColor.apply {
            clear()
            addAll(colorList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (listCart.isEmpty() || listProduct.isEmpty()) {
            return
        }
        Glide.with(context)
            .load(listProduct[position].imageFile)
            .fitCenter()
            .into(holder.imageCart)
        var sizeName: String = ""
        for (size in listSize) {
            if (size.idSize == listCart[position].idSize.toString()) {
                sizeName = size.name.toString()
            }
        }
        var colorName: String = ""
        for (color in listColor) {
            if (color.idColor == listCart[position].idColor.toString()) {
                colorName = color.name.toString()
            }
        }
        holder.productCart.text = listProduct[position].name
        holder.infoCart.text = "Size: $sizeName, Color: $colorName"
        val discount: Float = 1 - (listProduct[position].discount?.toFloat() ?: 0F)
        val price: Float = (listProduct[position].price?.toFloat() ?: 0F)
        holder.costCart.text =
            NumberFormat.getIntegerInstance(Locale.GERMANY).format(discount * price)
        holder.quantityCart.text = listCart[position].quantity.toString()
        holder.itemView.editText_quantity_cart.setText(listCart[position].quantity.toString())

        holder.container.setOnClickListener {
            selectedProduct.value = Product(
                listProduct[position].idProduct,
                listProduct[position].name,
                listProduct[position].idProductCode,
                listProduct[position].price,
                listProduct[position].quantity,
                listProduct[position].unit,
                listProduct[position].imageFile,
                listProduct[position].discount,
                listProduct[position].rating,
                listProduct[position].description,
                listProduct[position].isDeleted
            )
            isSelected.value = true
        }

        holder.itemView.button_delete_cart.setOnClickListener {
            idSize.value = listCart[position].idSize
            idColor.value = listCart[position].idColor
            idDeletedProduct.value = listProduct[position].idProduct?.toInt()
            listProduct.removeAt(position)
            listCart.removeAt(position)
            notifyDataSetChanged()
        }

        holder.itemView.button_increase_cart.setOnClickListener {
            if(listProduct[position].quantity?.toInt() ?: 0 == listCart[position].quantity) {
                Toast.makeText(context, "This product is out of stock!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            idProduct.value = listCart[position].idProduct
            listCart[position].quantity = listCart[position].quantity?.plus(1)
            quantity.value = listCart[position].quantity
            idSize.value = listCart[position].idSize
            idColor.value = listCart[position].idColor
            isUpdated.value = true
            notifyDataSetChanged()
        }

        holder.itemView.button_decrease_cart.setOnClickListener {
            idProduct.value = listCart[position].idProduct
            listCart[position].quantity = listCart[position].quantity?.minus(1)
            quantity.value = listCart[position].quantity
            idSize.value = listCart[position].idSize
            idColor.value = listCart[position].idColor
            isUpdated.value = true
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return listCart.size
    }
}