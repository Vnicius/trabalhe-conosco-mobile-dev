package io.github.vnicius.picpay.ui.cardpriming

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.vnicius.picpay.R
import io.github.vnicius.picpay.data.model.Contact
import kotlinx.android.synthetic.main.activity_card_priming.*

class CardPrimingActivity : AppCompatActivity() {

    private lateinit var mContact: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_priming)

        setSupportActionBar(tb_card_priming)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        mContact = intent.extras.getSerializable(ARG_DATA) as Contact
    }

    companion object {
        const val ARG_DATA = "contacts"
    }
}
