package io.github.vnicius.picpay.data.remote.retrofit.services

import io.github.vnicius.picpay.data.model.Contact
import retrofit2.Call
import retrofit2.http.GET

/**
 * Contacts service
 */
interface ContactsService {
    @GET("users")
    fun getContacts(): Call<List<Contact>>
}