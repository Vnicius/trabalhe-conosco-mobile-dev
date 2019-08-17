package io.github.vnicius.picpay.data.repository.contacts

import io.github.vnicius.picpay.api.APIInterface
import io.github.vnicius.picpay.api.PicpayAPI

/**
 * Contacts repository implementation with the API
 */
class ContactsRepositoryAPI: ContactsRepository {

    private val apiInstance: APIInterface = PicpayAPI()

    override fun getContacts() = apiInstance.getContacts()
}