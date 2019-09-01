package io.github.vnicius.picpay.data.model

data class TransactionRequest(val card_number: String,
                              val cvv: String,
                              val value: Float,
                              val expiry_date: String,
                              val destination_user_id: Long)