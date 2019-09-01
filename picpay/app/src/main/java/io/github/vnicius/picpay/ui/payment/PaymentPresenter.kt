package io.github.vnicius.picpay.ui.payment

import io.github.vnicius.picpay.data.model.Transaction
import io.github.vnicius.picpay.data.model.TransactionRequest
import io.github.vnicius.picpay.data.model.TransactionResponse
import io.github.vnicius.picpay.data.remote.PicpayAPIService
import io.github.vnicius.picpay.data.remote.RemoteInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class PaymentPresenter(val view: PaymentContract.View): PaymentContract.Presenter {

    private val mAPI: RemoteInterface = PicpayAPIService()

    override fun makeTransaction(transactionData: TransactionRequest) {
        val scope = CoroutineScope(Dispatchers.Main)

        scope.launch {
            val transactionResponse: TransactionResponse?

            try {
                transactionResponse = mAPI.makeTransaction(transactionData).await()

                if(transactionResponse == null) {
                    view.showMessage("Ocorreu algum error na transação")
                } else {
                    transactionResponse.cardNumber = transactionData.card_number
                    view.transactionDone(transactionResponse)
                }
            } catch (e: Exception) {
                view.showMessage("Ocorreu algum error")
            }
        }
    }
}