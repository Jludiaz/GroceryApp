package com.example.groceryapp

import android.util.Base64
import android.util.Log
import retrofit2.http.*

data class TokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int
)

interface KrogerApiService {

    // get an authorization token so server can send info back
    @FormUrlEncoded
    @POST("v1/connect/oauth2/token")
    suspend fun getAccessToken(
        @Header("Authorization") authHeader: String,
        @Field("grant_type") grantType: String = "client_credentials"
    ): TokenResponse

    // get local stores using retrofit
    @GET("v1/locations")
    suspend fun getStores(
        @Query("filter.zipCode.near") zipCode: String,
        @Header("Authorization") authHeader: String
    ): StoresResponse

     get product information using retro fit
    @GET("v1/products")
    suspend fun getProducts(
        @Query("filter.term") searchTerm: String,
        @Query("filter.locationId") locationId: String,
        @Header("Authorization") authHeader: String
    ): ProductsResponse
}

// handle token get(er) (created by chat GPT)
class TokenRepository {
    private val api = RetrofitClient.retrofit.create(KrogerApiService::class.java)
    private var cachedToken: String? = null

    suspend fun getToken(clientId: String, clientSecret: String): String {
        cachedToken?.let { return it }

        val credentials = "$clientId:$clientSecret"
        val encoded = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
        val authHeader = "Basic $encoded"

        val response = api.getAccessToken(authHeader)
        cachedToken = response.access_token
        return cachedToken!!
    }
}