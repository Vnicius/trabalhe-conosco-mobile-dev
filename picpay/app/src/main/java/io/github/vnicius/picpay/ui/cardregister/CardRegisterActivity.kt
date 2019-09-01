package io.github.vnicius.picpay.ui.cardregister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.redmadrobot.inputmask.MaskedTextChangedListener
import io.github.vnicius.picpay.R
import io.github.vnicius.picpay.data.model.Card
import io.github.vnicius.picpay.data.model.User
import io.github.vnicius.picpay.ui.payment.PaymentActivity
import kotlinx.android.synthetic.main.activity_card_register.*
import java.io.Serializable

class CardRegisterActivity : AppCompatActivity(), CardRegisterContract.View {

    private val mPresenter: CardRegisterContract.Presenter = CardRegisterPresenter(this)
    private var mCard: Card? = null
    private var mContact: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_register)

        setSupportActionBar(tb_card_register)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        handleIntent(intent)
    }

    companion object {
        const val ARG_DATA = "contact"
        const val ARG_CARD_DATA = "card"
    }

    override fun nextPage(card: Card) {
        val intent = Intent(baseContext, PaymentActivity::class.java)
        intent.putExtra(PaymentActivity.ARG_DATA_CONTACT, mContact as Serializable)
        intent.putExtra(PaymentActivity.ARG_DATA_CARD, card as Serializable)

        startActivity(intent)
    }

    override fun showMessage(message: String) {
        Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val cardNumberListener = MaskedTextChangedListener("[0000] [0000] [0000] [0000]", et_card_register_card_number)
        val validDateListener = MaskedTextChangedListener("[00]/[00]", et_card_register_validate)
        val cvvListener = MaskedTextChangedListener("[000]", et_card_register_cvv)

        mCard = intent.extras.getSerializable(ARG_CARD_DATA) as Card?
        mContact = intent.extras.getSerializable(ARG_DATA) as User?

        et_card_register_card_number.addTextChangedListener(cardNumberListener)
        et_card_register_card_number.onFocusChangeListener = cardNumberListener

        et_card_register_validate.addTextChangedListener(validDateListener)
        et_card_register_validate.onFocusChangeListener = validDateListener

        et_card_register_cvv.addTextChangedListener(cvvListener)
        et_card_register_cvv.onFocusChangeListener = cvvListener

        if(mCard != null) {
            et_card_register_card_number.setText(mCard!!.number)
            et_card_register_titular_name.setText(mCard!!.titular)
            et_card_register_validate.setText(mCard!!.validDate)
            et_card_register_cvv.setText(mCard!!.cvv)
        }

        btn_card_register_save.setOnClickListener {
            val cardNumber = et_card_register_card_number.text.toString().replace(" ", "")
            val titularName = et_card_register_titular_name.text.toString()
            val validDate = et_card_register_validate.text.toString()
            val cvv = et_card_register_cvv.text.toString()
            var id = -1

            if(mCard != null) {
                id = mCard!!.id
            }

            val card = Card(id, cardNumber, titularName, validDate, cvv)

            if(mCard != null) {
                mPresenter.updateCard(it.context, card)
            } else {
                mPresenter.saveCard(it.context, card)
            }
        }
    }
}
