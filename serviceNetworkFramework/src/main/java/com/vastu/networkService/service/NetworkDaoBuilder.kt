package com.vastu.networkService.service

import android.util.Log
import com.vastu.networkService.client.RetrofitClient
import com.vastu.networkService.serviceResListener.IOnServiceResponseListener
import com.vastu.networkService.util.Constants
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkDaoBuilder private constructor(
    private var  isRequestPost:Boolean,
    private var  isRequestPut:Boolean,
    private var isContentTypeJSON :Boolean,
    private var request:Any,
    private val urlEndPoint:String
) {
    fun sendApiRequest(listener: IOnServiceResponseListener) {
        val objRetrofit = RetrofitClient.getInstance()
        val requestBody: RequestBody
        val apiServiceGenerator = if (isRequestPost) {
            if (request is Map<*, *>) {
                objRetrofit.myPostRequestWithMap(urlEndPoint,request as Map<String, String>)
            } else {
                requestBody = if (isContentTypeJSON) {
                    RequestBody.create(
                        MediaType.parse(Constants.CONTENT_TYPE_JSON),
                        request as ByteArray
                    )
                } else {
                    RequestBody.create(
                        MediaType.parse(Constants.CONTENT_TYPE_VALUE),
                        request as ByteArray
                    )
                }

                objRetrofit.apiNetworkServicePost(urlEndPoint, requestBody)
            }
        }
        else if (isRequestPut) {
            requestBody = if (isContentTypeJSON) {
                RequestBody.create(
                    MediaType.parse(Constants.CONTENT_TYPE_JSON),
                    request as ByteArray
                )
            } else {
                RequestBody.create(
                    MediaType.parse(Constants.CONTENT_TYPE_VALUE),
                    request as ByteArray
                )
            }

            objRetrofit.apiNetworkServicePUT(urlEndPoint, requestBody)
        }
        else {
            objRetrofit.myGetRequest(urlEndPoint, request as Map<String, String>)
        }



         apiServiceGenerator.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                clearBuilderDetails()
                Log.d("Response",response.toString())
//                response.body()?.let { listener.onSuccessResponse(it.string()) }
                if (response.isSuccessful && response.code().toString()
                        .equals(ErrorCodes.succesCode)
                ) {
                    listener.onSuccessResponse( response.body()!!.string(),false)
                } else if (response.isSuccessful && response.code().toString()
                        .equals(ErrorCodes.successCodeNoContent)
                ) {
                    listener.onSuccessResponse("", false)
                } else if (response.isSuccessful && response.code().toString()
                        .equals(ErrorCodes.successCodeNoBody)
                ) {
                    listener.onSuccessResponse(response.body()!!.string(),false)
                } else if (!response.isSuccessful && response.code().toString()
                        .equals(ErrorCodes.errorCodeUnreacheableBody)
                ) {
                    listener.onFailureResponse(response.errorBody()!!.string())
                } else if (!response.isSuccessful && response.body() == null) {
                    listener.onFailureResponse(response.errorBody()!!.string())
                }
//                if (response.isSuccessful()) {
//
//                    Log.d("cateogry","success")
//                    // CategoryAdapter myAdapter = new CategoryAdapter(getActivity(), categoryList);
//                    //      mRecyclerView.setAdapter(new CategoryAdapter(category, R.layout.category_item_view, ));
//                    // mRecyclerView.setAdapter(myAdapter);
//                }
//                else
//                //  ApiErrorUtils.parseError(response);
//                    Log.d("Api hata", "failure")

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                clearBuilderDetails()
                listener.onFailureResponse(t.toString())
            }

        })

    }
    object Builder{
        var urlEndPoint :String =""
            private set
        var isRequestPost :Boolean = false
            private set
        var isRequestPut :Boolean = false
            private set
        var isContentTypeJSON :Boolean = true
            private set
        var setRequest :Any = ""
            private set
        fun setUrlEndPoint(endpoint :String)= run { apply { this.urlEndPoint = endpoint } }
        fun setIsRequestPost(isRequestPost: Boolean)= apply { this.isRequestPost = isRequestPost }
        fun setIsRequestPut(isRequestPut: Boolean)= apply { this.isRequestPut = isRequestPut }
        fun setIsContentTypeJSON(isContentTypeJSON: Boolean) = apply { this.isContentTypeJSON =isContentTypeJSON }
        fun setRequest(request: Any) = apply { this.setRequest = request }
        fun build() = NetworkDaoBuilder(isRequestPost, isRequestPut,isContentTypeJSON, setRequest, urlEndPoint)
    }

    fun clearBuilderDetails() {
        Builder.setUrlEndPoint("")
        Builder.setIsRequestPost(false)
        Builder.setIsRequestPut(false)
        Builder.setIsContentTypeJSON(true)
        Builder.setRequest("")

    }


}