package io.github.vnicius.picpay.data.local.card

import io.github.vnicius.picpay.data.local.BaseEntry

object CardEntry: BaseEntry {
    const val TABLE_NAME = "cards"
    const val COLUMN_NAME_NUMBER = "number"
    const val COLUMN_NAME_TITULAR = "titular"
    const val COLUMN_NAME_VALID_DATE = "valid_date"
    const val COLUMN_NAME_CVV = "cvv"
}