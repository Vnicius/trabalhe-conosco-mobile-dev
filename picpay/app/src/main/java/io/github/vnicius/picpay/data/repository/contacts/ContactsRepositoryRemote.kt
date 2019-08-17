package io.github.vnicius.picpay.data.repository.contacts

import io.github.vnicius.picpay.data.remote.RemoteInterface
import io.github.vnicius.picpay.data.remote.PicpayAPIService

/**
 * Contacts repository implementation with the API
 */
class ContactsRepositoryRemote: ContactsRepository {

    private val apiInstance: RemoteInterface = PicpayAPIService()

    override fun getContacts() = apiInstance.getContacts()
}