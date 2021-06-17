package com.example.fashionecommercemobileapp.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.adapters.BillAdapter
import com.example.fashionecommercemobileapp.model.*
import com.example.fashionecommercemobileapp.retrofit.repository.BillRepository
import com.example.fashionecommercemobileapp.viewmodels.BillViewModel
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.order_item.*

class OrderActivity : AppCompatActivity() {
    private lateinit var billRecyclerView: RecyclerView
    private var billViewModel: BillViewModel? = null
    private lateinit var billAdapter: BillAdapter
    private lateinit var isClicked: LiveData<Boolean>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        setUpOrderRecyclerView()
        fetchData()

        isClicked = billAdapter.getIsClicked()
        val observer = Observer<Boolean> { it ->
            if (it) {
                val bill = billAdapter.getBill().value
                val intent = Intent(this, OrderDetailsActivity::class.java).apply {
                    if (bill != null) {
                        putExtra("idBill", bill.id)
                        putExtra("idAddress", bill.idAddress)
                        putExtra("idAccount", bill.idAccount)
                        putExtra("date", bill.invoiceDate)
                        putExtra("total", bill.totalMoney)
                        putExtra("status", bill.status)
                        putExtra("isRated", bill.isRated)
                    }
                }
                startActivity(intent)
            }
        }
        isClicked.observe(this, observer)
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    private fun setUpOrderRecyclerView() {
        billRecyclerView = findViewById(R.id.order_recycler)
        billAdapter = BillAdapter(this, arrayListOf())
        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false)
        billRecyclerView.layoutManager = layoutManager
        billRecyclerView.adapter = billAdapter
    }

    private fun fetchData() {
        val spf = getSharedPreferences("Login", Context.MODE_PRIVATE)
        val idAccount = spf.getString("Id", null)

        BillRepository.Companion.setContext(this@OrderActivity)
        billViewModel = ViewModelProviders.of(this).get(BillViewModel::class.java)
        billViewModel!!.init()
        billViewModel!!.getBillData(idAccount.toString())
            ?.observe(this, Observer {
                billAdapter.apply {
                    changeData(it)
                    notifyDataSetChanged()
                }
            })
    }

    fun onClickBack(view: View) {
        super.onBackPressed()
    }
}