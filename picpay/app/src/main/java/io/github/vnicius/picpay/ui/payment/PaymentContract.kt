package io.github.vnicius.picpay.ui.payment

import io.github.vnicius.picpay.data.model.Transaction
import io.github.vnicius.picpay.data.model.TransactionRequest
import io.github.vnicius.picpay.data.model.TransactionResponse

interface PaymentContract {

    interface View {
        fun showMessage(message: String)
        fun transactionDone(transaction: TransactionResponse)
    }

    interface Presenter {
        fun makeTransaction(transactionData: TransactionRequest)
    }
}