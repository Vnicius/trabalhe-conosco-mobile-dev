package io.github.vnicius.picpay.data.remote

import io.github.vnicius.picpay.data.remote.retrofit.RetrofitInitializer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

/**
 * Connection with the PicPay API
 */
class PicpayAPIService: RemoteInterface {

    private val retrofitInstance = RetrofitInitializer()

    override fun getContacts() = GlobalScope.async {
        val contactsCall = retrofitInstance.contactsService().getContacts()
        contactsCall.execute().body()
    }

    override fun makeTransaction() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}