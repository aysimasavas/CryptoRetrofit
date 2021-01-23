package com.aysimasavas.cryptoretrofitkt.service

import com.aysimasavas.cryptoretrofitkt.model.CryptoModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import java.util.*

interface CryptoApi {


    //GET,POST,UPDATE,DELETE

    //c276b72fd51f8406d73b4ebb9e3fac49

    //https://api.nomics.com/v1/prices?key=c276b72fd51f8406d73b4ebb9e3fac49

    @GET("prices?key=c276b72fd51f8406d73b4ebb9e3fac49")

    fun getData(): Observable<List<CryptoModel>>

   // fun getData(): Call<List<CryptoModel>>


}