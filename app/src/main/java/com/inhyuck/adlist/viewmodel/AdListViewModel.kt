package com.inhyuck.adlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.inhyuck.adlist.BuildConfig
import com.inhyuck.adlist.api.AppiaService
import com.inhyuck.adlist.api.GetAdsResponse
import com.inhyuck.adlist.db.MainDB
import com.inhyuck.adlist.db.entity.Ad
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.Executors

class AdListViewModel (
    private val appiaService: AppiaService,
    private val db: MainDB
) : ViewModel(){
    var adList : LiveData<List<Ad>>

    init {
        fetchUpdate()
        adList = db.adDao().loadAll()
    }

    var foundError = MutableLiveData<String?>()

    fun fetchUpdate(){
        //TODO: set loading status
        //?id=236&password=OVUJ1DJN&siteId=10777&deviceId=4230&sessionId=techtestsession&totalCampaignsRequested=10&lname=kim
        appiaService.getAds(
            id = 236,
            password = "OVUJ1DJN",
            siteId = 10777,
            deviceId = 4230,
            sessionId = "techtestsession",
            totalCampaignsRequested = 10,
            lname = "kim").enqueue(
            object : Callback<GetAdsResponse> {
                override fun onFailure(call: Call<GetAdsResponse>, t: Throwable) {
                    // handle failure
                    foundError.value = t.message
                }

                override fun onResponse(
                    call: Call<GetAdsResponse>,
                    response: Response<GetAdsResponse>
                ) {
                    foundError.value = null

                    //parse data received
                    if (response.body() != null && response.body()?.ads != null){

                        //reset ads
                        Executors.newSingleThreadExecutor().execute {
                            db.adDao().deleteAll()
                            db.adDao().insert(response.body()?.ads)
                        }
                    }
                }
            })
        //TODO: set loading status
    }
}

object AdListVMFactory : ViewModelProvider.Factory {
    lateinit var mainDB: MainDB

    fun setDB(db: MainDB) : AdListVMFactory{
        mainDB = db
        return this
    }

    private fun getService() :AppiaService {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val clientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG){
            clientBuilder.addInterceptor(interceptor)
        }

        val client = clientBuilder.build()

        return Retrofit.Builder()
            .baseUrl("http://ads.appia.com/")
            .addConverterFactory(SimpleXmlConverterFactory.create()).client(
                client
            ).build().create(AppiaService::class.java)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AdListViewModel(getService(), mainDB) as T
    }
}