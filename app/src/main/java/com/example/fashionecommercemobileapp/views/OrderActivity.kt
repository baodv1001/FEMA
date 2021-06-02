package com.example.fashionecommercemobileapp.views

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
import com.example.fashionecommercemobileapp.viewmodels.BillViewModel

class OrderActivity : AppCompatActivity() {
    lateinit var billRecyclerView: RecyclerView
    private var billViewModel: BillViewModel? = null
    private var idCart: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val intent: Intent = intent
        val idAccount: Int? = intent.getIntExtra("idAccount",0)
        idCart = intent.getIntExtra("idCart", 0)

        BillRepository.Companion.setContext(this@OrderActivity)
        billViewModel = ViewModelProviders.of(this).get(BillViewModel::class.java)
        billViewModel!!.init()
        billViewModel!!.getBillData(idAccount.toString())?.observe(this, Observer { setUpOrderRecyclerView(it) })
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

    fun onClickViewDetails(view: View) {
        val intent = Intent(this, CheckOutActivity::class.java).apply {
            putExtra("idCart", idCart)
            putExtra("idAccount", idCart)
            putExtra("isViewing", true)
        }
        startActivity(intent)
    }
}