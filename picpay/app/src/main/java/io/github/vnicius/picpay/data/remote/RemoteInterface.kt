package io.github.vnicius.picpay.data.remote

import io.github.vnicius.picpay.data.model.Transaction
import io.github.vnicius.picpay.data.model.TransactionRequest
import io.github.vnicius.picpay.data.model.TransactionResponse
import io.github.vnicius.picpay.data.model.User
import kotlinx.coroutines.Deferred

/**
 * API connection interface
 */
interface RemoteInterface {
    /**
     * Get contacts
     * @return
     */
    fun getContacts(): Deferred<List<User>?>

    /**
     * Make the transaction
     */
    fun makeTransaction(transaction: TransactionRequest): Deferred<TransactionResponse?>
}