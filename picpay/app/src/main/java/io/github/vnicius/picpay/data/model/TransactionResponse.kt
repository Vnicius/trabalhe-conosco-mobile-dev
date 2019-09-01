package io.github.vnicius.picpay.data.model

import java.io.Serializable

class TransactionResponse (val transaction: Transaction, var cardNumber: String): Serializable