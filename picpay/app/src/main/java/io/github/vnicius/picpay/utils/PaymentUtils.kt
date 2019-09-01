package io.github.vnicius.picpay.utils

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class PaymentUtils {
    companion object{
        fun valueParser(value: String): Float {
            if (value.isBlank()) {
                return .0f
            }

            val valueParsed = value.replace(',','.')

            try {
                return valueParsed.toFloat()
            } catch (e : Exception) {
                return .0f
            }
        }

        fun moneyParser(value: Float): String {
            val intPart = value.toInt()
            val floatPart = (value - intPart).toInt()

            return "$intPart,$floatPart${if (floatPart < 10) "0" else ""}"
        }

        fun timeParser(timestamp: Long): String {
            val date = Date(timestamp)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val timeFormat = SimpleDateFormat("mm:ss")

            return "${dateFormat.format(date)} às ${timeFormat.format(date)}"

        }
    }
}