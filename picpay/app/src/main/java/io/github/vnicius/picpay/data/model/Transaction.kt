package io.github.vnicius.picpay.data.model

import java.io.Serializable

data class Transaction (val id: Long, val timestamp: Long, val value: Float,
                        val destination_user: User, val success: Boolean, val status: String): Serializable