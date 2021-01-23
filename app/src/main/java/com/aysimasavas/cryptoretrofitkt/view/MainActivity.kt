package com.aysimasavas.cryptoretrofitkt.view

import android.content.Context
import android.content.Intent
import android.location.GnssAntennaInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.*
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aysimasavas.cryptoretrofitkt.R
import com.aysimasavas.cryptoretrofitkt.adapter.RecyclerviewAdapter
import com.aysimasavas.cryptoretrofitkt.model.CryptoModel

import com.aysimasavas.cryptoretrofitkt.service.CryptoApi
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(),RecyclerviewAdapter.Listener{



    private val BASE_URL ="https://api.nomics.com/v1/"
    private var cryptoModels:ArrayList<CryptoModel>?=null
    private var recyclerviewAdapter: RecyclerviewAdapter?=null

    private var compositeDisposable:CompositeDisposable?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compositeDisposable= CompositeDisposable()
        val layoutMan : RecyclerView.LayoutManager=LinearLayoutManager(this)

        recyclerview.layoutManager= layoutMan

        frame_layout.visibility=View.GONE




        loadData()

    }


    //c276b72fd51f8406d73b4ebb9e3fac49

    //https://api.nomics.com/v1/prices?key=c276b72fd51f8406d73b4ebb9e3fac49



    private fun loadData(){
        val retrofit= Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoApi::class.java)


        compositeDisposable?.add(retrofit.getData().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleRespose))

      // val service= retrofit.create(CryptoApi::class.java)
      // val call=service.getData()


        /*
        call.enqueue(object: Callback<List<CryptoModel>>
        {





            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {

                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            )
            {
                if (response.isSuccessful)
                {

                    response.body()?.let {
                        cryptoModels = ArrayList(it)

                        recyclerviewAdapter=RecyclerviewAdapter(cryptoModels!!,this@MainActivity)
                        recyclerview.adapter=recyclerviewAdapter
                    }
                }

            }


        })*/


    }


    private fun handleRespose(cryptoList:List<CryptoModel>)
    {

        cryptoModels = ArrayList(cryptoList)

        recyclerviewAdapter=RecyclerviewAdapter(cryptoModels!!,this@MainActivity)
        recyclerview.adapter=recyclerviewAdapter

    }


    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this,"Clicked: ${cryptoModel.currency}",Toast.LENGTH_LONG).show()


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


      if(item.itemId==R.id.menu)

      {
          recyclerview.visibility=View.GONE
          frame_layout.visibility=View.VISIBLE



         val favFragment:favFragment= favFragment()

          supportFragmentManager.beginTransaction().replace(R.id.frame_layout,favFragment).commit()

      }

        if (item.itemId==R.id.back)
        {

            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater= menuInflater

        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)


    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}