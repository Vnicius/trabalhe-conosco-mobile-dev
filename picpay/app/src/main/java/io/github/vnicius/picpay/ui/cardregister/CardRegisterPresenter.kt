package io.github.vnicius.picpay.ui.cardregister

import android.content.Context
import io.github.vnicius.picpay.data.model.Card
import io.github.vnicius.picpay.data.repository.cards.CardsRepository
import io.github.vnicius.picpay.data.repository.cards.CardsRepositoryLocal
import io.github.vnicius.picpay.utils.CardRegisterValidationUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

class CardRegisterPresenter(val view: CardRegisterContract.View): CardRegisterContract.Presenter {

    private lateinit var cardsRepository: CardsRepository

    override fun saveCard(context: Context, card: Card) {
        if(!CardRegisterValidationUtils.validCardNumber(card.number)) {
            view.showMessage("Número do cartão inválido")
        } else if(!CardRegisterValidationUtils.validTitularName(card.titular)) {
            view.showMessage("Nome do titular inválido")
        } else if(!CardRegisterValidationUtils.validDate(card.validDate)) {
            view.showMessage("Data da validade inválido")
        } else if(!CardRegisterValidationUtils.validCVV(card.cvv)) {
            view.showMessage("CVV inválido")
        } else {
            val scope = CoroutineScope(Dispatchers.Main)
            cardsRepository = CardsRepositoryLocal(context)

            scope.launch {
                try {
                    coroutineScope {
                        cardsRepository.saveCard(card).await()
                        view.nextPage()
                    }
                }catch (e: Exception) {
                    view.showMessage("Ocorreu algum erro")
                }
            }
        }
    }
}