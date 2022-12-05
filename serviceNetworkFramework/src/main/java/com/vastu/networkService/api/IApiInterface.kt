package com.vastu.networkService.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface IApiInterface {
    @POST()
    fun getComments(@Url url:String, @Body request: RequestBody): Call<ResponseBody>

    @GET
    fun myGetRequest(@Url url: String, map: Map<String, String>): Call<ResponseBody>

    @POST
    fun myPostRequestWithMap(@Url url:String,@QueryMap(encoded = true) list: Map<String, String>):Call<ResponseBody>


    @GET
    fun apiNetworkServiceGet(@Url url:String,@QueryMap(encoded = true) list: Map<String, String>):Call<ResponseBody>

    @POST
    fun apiNetworkServicePost(@Url url:String,@Body list: RequestBody):Call<ResponseBody>

    @POST
    fun apiNetworkServicePostWithMap(@Url url:String,@QueryMap(encoded = true) list: Map<String, String>):Call<ResponseBody>

    @PUT
    fun apiNetworkServicePUT(@Url url:String,@Body list: RequestBody):Call<ResponseBody>
}