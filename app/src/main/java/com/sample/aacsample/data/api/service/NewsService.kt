package com.sample.aacsample.data.api.service

import com.sample.aacsample.data.api.model.Headlines
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by y_hisano on 2018/07/26.
 */
interface NewsService {

    @GET("top-headlines")
    fun headline(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String,
        @Query("category") category: String
    ): Call<Headlines>
}
