package io.github.vnicius.picpay.data.repository.cards

import io.github.vnicius.picpay.data.model.Card
import kotlinx.coroutines.Deferred

interface CardsRepository {
    fun saveCard(card: Card): Deferred<Long>
    fun uploadCard(card: Card): Deferred<Boolean>
    fun getAllCards(): Deferred<List<Card>>
}