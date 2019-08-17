package io.github.vnicius.picpay.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import io.github.vnicius.picpay.R

/**
 * [Fragment] show no connect message
 * @property tryAgainHandler handle the "try again" button
 */
class NoConnectionFragment(private val tryAgainHandler: (() -> Unit)) : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_no_connection, container, false)

        view.findViewById<Button>(R.id.btn_try_again).setOnClickListener(this)

        return view
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.btn_try_again -> tryAgainHandler()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(tryAgainHandler: (() -> Unit)) = NoConnectionFragment(tryAgainHandler)
    }
}
