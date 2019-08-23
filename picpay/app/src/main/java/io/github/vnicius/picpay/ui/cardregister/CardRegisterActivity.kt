package io.github.vnicius.picpay.ui.cardregister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.redmadrobot.inputmask.MaskedTextChangedListener
import io.github.vnicius.picpay.R
import io.github.vnicius.picpay.data.model.Card
import kotlinx.android.synthetic.main.activity_card_register.*

class CardRegisterActivity : AppCompatActivity(), CardRegisterContract.View {

    private val mPresenter: CardRegisterContract.Presenter = CardRegisterPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_register)

        setSupportActionBar(tb_card_register)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val cardNumberListener = MaskedTextChangedListener("[0000] [0000] [0000] [0000]", et_card_register_card_number)
        val validDateListener = MaskedTextChangedListener("[00]/[00]", et_card_register_validate)
        val cvvListener = MaskedTextChangedListener("[000]", et_card_register_cvv)

        et_card_register_card_number.addTextChangedListener(cardNumberListener)
        et_card_register_card_number.onFocusChangeListener = cardNumberListener

        et_card_register_validate.addTextChangedListener(validDateListener)
        et_card_register_validate.onFocusChangeListener = validDateListener

        et_card_register_cvv.addTextChangedListener(cvvListener)
        et_card_register_cvv.onFocusChangeListener = cvvListener

        btn_card_register_save.setOnClickListener {
            val cardNumber = et_card_register_card_number.text.toString().replace(" ", "")
            val titularName = et_card_register_titular_name.text.toString()
            val validDate = et_card_register_validate.text.toString()
            val cvv = et_card_register_cvv.text.toString()

            mPresenter.saveCard(it.context, Card(0, cardNumber, titularName, validDate, cvv))
        }
    }

    companion object {
        const val ARG_DATA = "contact"
    }

    override fun nextPage() {
        Toast.makeText(baseContext, "Saved", Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
    }
}
