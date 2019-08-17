package io.github.vnicius.picpay.data.local

import io.github.vnicius.picpay.data.local.card.CardEntry

object BaseCommands {
    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE IF NOT EXISTS ${CardEntry.TABLE_NAME} (" +
                "${BaseEntry.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${CardEntry.COLUMN_NAME_TITULAR} TEXT NOT NULL," +
                "${CardEntry.COLUMN_NAME_NUMBER} TEXT NOT NULL," +
                "${CardEntry.COLUMN_NAME_VALID_DATE} VARCHAR(5) NOT NULL," +
                "${CardEntry.COLUMN_NAME_CVV} VARCHAR(3));"

    const val SQL_DELETE_ENTRIES = "DELETE TABLE IF EXISTS ${CardEntry.TABLE_NAME}"
}