package com.example.fashionecommercemobileapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionecommercemobileapp.Adapter.AddressAdapter
import com.example.fashionecommercemobileapp.Adapter.OrderAdapter
import com.example.fashionecommercemobileapp.Models.Address
import com.example.fashionecommercemobileapp.Models.Example
import com.example.fashionecommercemobileapp.Models.Popular
import com.example.fashionecommercemobileapp.Retrofit.ApiInterface
import com.example.fashionecommercemobileapp.Retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressActivity : AppCompatActivity() {


    lateinit var popularRecyclerView: RecyclerView

    lateinit var popularAdapter: AddressAdapter
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.address)
        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            super.onBackPressed()
        }
        var retrofit: RetrofitClient = RetrofitClient()
        var apiInterface: ApiInterface =
            retrofit.getRetrofitInstance()!!.create(ApiInterface::class.java)

        val call: Call<List<Example>> = apiInterface.getAllData()
        call.enqueue(object : Callback<List<Example>> {
            override fun onResponse(call: Call<List<Example>>, response: Response<List<Example>>) {
                val fashionAppDataList: List<Example> = response.body()!!
                fashionAppDataList[0].addressinfo?.let { getPopularData(it) }

                // lets run it.
                // we have fetched data from server.
                // now we have to show data in app using recycler view
                // lets make recycler view adapter
                // we have setup and bind popular section
                // in a same way we add recommended and all menu items
                // we add two adapter class for allmenu and recommended items.
                // so lets do it fast.
            }

            override fun onFailure(call: Call<List<Example>>, t: Throwable?) {
                Toast.makeText(
                    this@AddressActivity,
                    t.toString(),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
    }

    private fun getPopularData(addressList: List<Address>) {
        popularRecyclerView = findViewById<View>(R.id.address_recycler) as RecyclerView
        popularAdapter = AddressAdapter(this, addressList)
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        popularRecyclerView.setLayoutManager(layoutManager)
        popularRecyclerView.setAdapter(popularAdapter)

    }

}