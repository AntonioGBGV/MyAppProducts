package com.example.myappproducts

import com.example.myappproducts.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getAllProducts() : Response<ProductResponse>
}
