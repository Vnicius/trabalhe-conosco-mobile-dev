package io.github.vnicius.picpay.data.remote.retrofit.services

import io.github.vnicius.picpay.data.model.Transaction
import io.github.vnicius.picpay.data.model.User
import io.github.vnicius.picpay.data.model.TransactionRequest
import io.github.vnicius.picpay.data.model.TransactionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Contacts service
 */
interface ContactsService {
    @GET("users")
    fun getContacts(): Call<List<User>>

    @Headers("Content-Type: application/json")
    @POST("transaction")
    fun doTransaction(@Body transaction: TransactionRequest): Call<TransactionResponse>
}