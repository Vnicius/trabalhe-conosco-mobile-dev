package io.github.vnicius.picpay.data.remote.retrofit

import io.github.vnicius.picpay.data.remote.retrofit.services.ContactsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit initializer to connect with the API
 */
class RetrofitInitializer {

    private val instance = Retrofit.Builder()
        .baseUrl("http://careers.picpay.com/tests/mobdev/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * Get the [ContactsService] object
     * @return [ContactsService]
     */
    fun contactsService() = instance.create(ContactsService::class.java)
}