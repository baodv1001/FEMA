package com.example.fashionecommercemobileapp.views

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.adapters.BillAdapter
import com.example.fashionecommercemobileapp.model.Bill
import com.example.fashionecommercemobileapp.retrofit.repository.BillRepository
import com.example.fashionecommercemobileapp.viewmodels.OrderViewModel

class OrderActivity : AppCompatActivity() {
    lateinit var billRecyclerView: RecyclerView
    private var orderViewModel: OrderViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val sp1 = getSharedPreferences("Login", Context.MODE_PRIVATE)
        val idAccount = sp1.getString("Id", null)

        BillRepository.Companion.setContext(this@OrderActivity)
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel::class.java)
        orderViewModel!!.init()

        orderViewModel!!.getBillData(idAccount.toString())?.observe(this, Observer { setUpOrderRecyclerView(it) })
    }

    private fun setUpOrderRecyclerView(listBill: List<Bill>) {
        billRecyclerView = findViewById(R.id.order_recycler)
        val billAdapter: BillAdapter = BillAdapter(this, listBill)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false)
        billRecyclerView.layoutManager = layoutManager
        billRecyclerView.adapter = billAdapter
    }
    fun onClickBack(view: View) {
        super.onBackPressed()
    }
}