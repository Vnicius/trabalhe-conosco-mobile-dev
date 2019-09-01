package io.github.vnicius.picpay.data.repository.contacts

import io.github.vnicius.picpay.data.model.User
import kotlinx.coroutines.Deferred

/**
 * Contacts repository interface
 */
interface ContactsRepository {
    /**
     * Get the contacts in async way
     */
    fun getContacts(): Deferred<List<User>?>
}