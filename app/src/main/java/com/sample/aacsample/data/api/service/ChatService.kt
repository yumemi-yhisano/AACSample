package com.sample.aacsample.data.api.service

import com.sample.aacsample.data.api.model.Chat
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * Created by y_hisano on 2018/07/26.
 */
interface ChatService {

    @Multipart
    @POST("smalltalk")
    fun smalltalk(
        @Part("apikey") apiKey: RequestBody,
        @Part("query") query: RequestBody): Call<Chat>
}
