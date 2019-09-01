package io.github.vnicius.picpay.data.model

import java.io.Serializable

data class Card (var id: Int,
                 val number: String,
                 val titular: String,
                 val validDate: String,
                 val cvv: String): Serializable