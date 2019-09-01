package io.github.vnicius.picpay.ui.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import io.github.vnicius.picpay.R
import io.github.vnicius.picpay.data.model.*
import io.github.vnicius.picpay.ui.cardregister.CardRegisterActivity
import io.github.vnicius.picpay.ui.contacts.ContactsActivity
import io.github.vnicius.picpay.utils.PaymentUtils
import kotlinx.android.synthetic.main.activity_payment.*
import java.io.Serializable

class PaymentActivity : AppCompatActivity(), PaymentContract.View {

    private val mPreseter: PaymentContract.Presenter = PaymentPresenter(this)
    private lateinit var contact: User
    private lateinit var card: Card

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        setSupportActionBar(tb_payment)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        handleIntent(intent)
    }

    companion object{
        const val ARG_DATA_CONTACT = "contact"
        const val ARG_DATA_CARD = "card"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun handleIntent(intent: Intent) {
        val extras = intent.extras

        contact = extras.getSerializable(ARG_DATA_CONTACT) as User
        card = extras.getSerializable(ARG_DATA_CARD) as Card

        Picasso.get().load(contact.img).into(iv_payment_contact_avatar)
        tv_payment_contact_username.text = contact.username

        et_payment_value.onFocusChangeListener = View.OnFocusChangeListener { view, focus ->
            if(focus) {
                tv_payment_monetary_unit.setTextColor(ContextCompat.getColor(view.context, R.color.colorAccent))
            } else {
                tv_payment_monetary_unit.setTextColor(ContextCompat.getColor(view.context, R.color.colorSecondaryText))
            }
        }

        et_payment_value.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(e: Editable) {
                var value = e.toString().replace(",","")

                if(value.length >= 2) {
                    if (value.length == 2) {
                        value = ",$value"
                    } else {
                        value = "${value.slice(0..(value.length - 3))},${value.slice((value.length - 2)..(value.length - 1))}"
                    }
                }

                et_payment_value.removeTextChangedListener(this)

                e.replace(0, e.length, value)

                et_payment_value.addTextChangedListener(this)
            }

            override fun beforeTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {
                //toStart = count < after && start == 0
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        tv_payment_card.text = "Card ${card.number.subSequence(12,16)}"

        btn_payment_edit_card.setOnClickListener {
            val newIntent = Intent(it.context, CardRegisterActivity::class.java)
            newIntent.putExtra(CardRegisterActivity.ARG_DATA, contact as Serializable)
            newIntent.putExtra(CardRegisterActivity.ARG_CARD_DATA, card as Serializable)

            startActivity(newIntent)
        }

        btn_payment_pay.setOnClickListener {
            var value = PaymentUtils.valueParser(et_payment_value.text.toString())

            if (value != .0f) {
                val transactionData = TransactionRequest(card.number, card.cvv, value, card.validDate, contact.id)
                mPreseter.makeTransaction(transactionData)
            } else {
                showMessage("Escolha o valor corretamente")
            }
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show()
    }

    override fun transactionDone(transaction: TransactionResponse) {
        val intent = Intent(baseContext, ContactsActivity::class.java)
        intent.putExtra(ContactsActivity.ARG_TRASACTION, transaction as Serializable)
        startActivity(intent)
    }
}
