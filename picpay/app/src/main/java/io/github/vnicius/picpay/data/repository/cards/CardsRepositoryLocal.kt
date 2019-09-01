package io.github.vnicius.picpay.data.repository.cards

import android.content.ContentValues
import android.content.Context
import io.github.vnicius.picpay.data.local.BaseEntry
import io.github.vnicius.picpay.data.local.DBHelper
import io.github.vnicius.picpay.data.local.card.CardEntry
import io.github.vnicius.picpay.data.model.Card
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class CardsRepositoryLocal(context: Context): CardsRepository{

    private val dbHelper = DBHelper(context)

    override fun saveCard(card: Card) = GlobalScope.async {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(CardEntry.COLUMN_NAME_NUMBER, card.number)
            put(CardEntry.COLUMN_NAME_TITULAR, card.titular)
            put(CardEntry.COLUMN_NAME_VALID_DATE, card.validDate)
            put(CardEntry.COLUMN_NAME_CVV, card.cvv)
        }

        db.insert(CardEntry.TABLE_NAME, null, values)
    }

    override fun getAllCards() = GlobalScope.async {
        val db = dbHelper.readableDatabase

        // projection with the columns to get
        val projection = arrayOf(BaseEntry.COLUMN_NAME_ID,
            CardEntry.COLUMN_NAME_NUMBER,
            CardEntry.COLUMN_NAME_TITULAR,
            CardEntry.COLUMN_NAME_VALID_DATE,
            CardEntry.COLUMN_NAME_CVV)

        val cursor = db.query(CardEntry.TABLE_NAME, projection, null, null, null, null, null)
        var cards: MutableList<Card> = mutableListOf()

        with(cursor) {
            while (moveToNext()) {
                cards.add(Card(id = getInt(getColumnIndexOrThrow(BaseEntry.COLUMN_NAME_ID)),
                    number = getString(getColumnIndexOrThrow(CardEntry.COLUMN_NAME_NUMBER)),
                    titular = getString(getColumnIndexOrThrow(CardEntry.COLUMN_NAME_TITULAR)),
                    validDate = getString(getColumnIndexOrThrow(CardEntry.COLUMN_NAME_VALID_DATE)),
                    cvv = getString(getColumnIndexOrThrow(CardEntry.COLUMN_NAME_CVV))))
            }
        }

        cards.toList()
    }

    override fun uploadCard(card: Card) = GlobalScope.async{
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(CardEntry.COLUMN_NAME_NUMBER, card.number)
            put(CardEntry.COLUMN_NAME_TITULAR, card.titular)
            put(CardEntry.COLUMN_NAME_VALID_DATE, card.validDate)
            put(CardEntry.COLUMN_NAME_CVV, card.cvv)
        }

        val selection = "${BaseEntry.COLUMN_NAME_ID} LIKE ?"
        val selectionArgs = arrayOf(card.id.toString())
        val count = db.update(CardEntry.TABLE_NAME, values, selection, selectionArgs)

        count != 0
    }

    fun finish() {
        dbHelper.close()
    }
}