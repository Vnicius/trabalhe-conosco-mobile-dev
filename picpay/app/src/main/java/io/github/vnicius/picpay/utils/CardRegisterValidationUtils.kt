package io.github.vnicius.picpay.utils

import java.text.SimpleDateFormat
import java.util.*

class CardRegisterValidationUtils {
    companion object {
        fun validCardNumber(cardNumber: String): Boolean {
            val cardNumberRegex = Regex("""\d{16}""")

            return cardNumberRegex.matches(cardNumber)
        }

        fun validTitularName(name: String): Boolean {
            val nameRegex = Regex("""(\w| )+""")

            return nameRegex.matches(name)
        }

        fun validDate(date: String): Boolean {
            val dateRegex = Regex("""\d{2}/\d{2}""")

            if (dateRegex.matches(date)) {
                val month= date.subSequence(0,2).toString().toInt()
                val year = ("20" + date.subSequence(3,5).toString()).toInt()

                return (month in 1..12 && year >= Calendar.getInstance().get(Calendar.YEAR) && month >= Calendar.getInstance().get(Calendar.MONTH))
            }

            return false
        }

        fun validCVV(cvv: String): Boolean {
            val cvvRegex = Regex("""\d{3}""")

            return cvvRegex.matches(cvv)
        }
    }
}