package com.inhyuck.adlist.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AppiaService {

    @GET("getAds")
    fun getAds(@Query("id") id:Int,
               @Query("password") password:String,
               @Query("siteId") siteId:Int,
               @Query("deviceId") deviceId:Int,
               @Query("sessionId") sessionId:String,
               @Query("totalCampaignsRequested") totalCampaignsRequested:Int,
               @Query("lname") lname:String
    ): Call<GetAdsResponse>
}