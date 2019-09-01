package io.github.vnicius.picpay.data.remote

import android.util.Log
import io.github.vnicius.picpay.data.model.Transaction
import io.github.vnicius.picpay.data.model.TransactionRequest
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

    override fun makeTransaction(transaction: TransactionRequest) = GlobalScope.async {
        val transactionResponse = retrofitInstance.contactsService().doTransaction(transaction)
        transactionResponse.execute().body()
    }

}