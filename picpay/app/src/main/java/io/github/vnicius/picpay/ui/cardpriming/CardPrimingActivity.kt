package io.github.vnicius.picpay.ui.cardpriming

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.vnicius.picpay.R
import io.github.vnicius.picpay.data.model.User
import io.github.vnicius.picpay.ui.cardregister.CardRegisterActivity
import kotlinx.android.synthetic.main.activity_card_priming.*
import java.io.Serializable

class CardPrimingActivity : AppCompatActivity() {

    private lateinit var mContact: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_priming)

        setSupportActionBar(tb_card_priming)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        mContact = intent.extras.getSerializable(ARG_DATA) as User

        btn_card_priming_register_card.setOnClickListener {
            val intent = Intent(it.context, CardRegisterActivity::class.java)
            intent.putExtra(CardRegisterActivity.ARG_DATA, mContact as Serializable)
            startActivity(intent)
        }
    }

    companion object {
        const val ARG_DATA = "contact"
    }
}
