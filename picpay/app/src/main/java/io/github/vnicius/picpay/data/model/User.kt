package io.github.vnicius.picpay.data.model

import java.io.Serializable

/**
 * User data class
 */
data class User(val id: Long, val name: String, val img: String, val username: String): Serializable