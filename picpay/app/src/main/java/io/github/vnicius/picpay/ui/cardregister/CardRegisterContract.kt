package io.github.vnicius.picpay.ui.cardregister

import android.content.Context
import io.github.vnicius.picpay.data.model.Card

interface CardRegisterContract {

    interface View{
        fun nextPage()
        fun showMessage(message: String)
    }

    interface Presenter{
        fun saveCard(context: Context, card: Card)
    }
}