package io.github.vnicius.picpay.api

import io.github.vnicius.picpay.api.retrofit.RetrofitInitializer
import io.github.vnicius.picpay.data.model.Contact
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

/**
 * Connection with the PicPay API
 */
class PicpayAPI: APIInterface {

    private val retrofitInstance = RetrofitInitializer()

    override fun getContacts() = GlobalScope.async {
        val contactsCall = retrofitInstance.contactsService().getContacts()
        contactsCall.execute().body()
    }

    override fun makeTransaction() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}