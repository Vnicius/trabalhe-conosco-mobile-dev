package io.github.vnicius.picpay.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import io.github.vnicius.picpay.R

/**
 * [Fragment] to show "no result" message for a query
 * @property query
 */
class NoResultFragment(private val query: String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_no_result, container, false)

        view.findViewById<TextView>(R.id.tv_no_result).text = "Sem resultados para \"$query\""

            return view
    }

    companion object {
        @JvmStatic
        fun newInstance(query: String) = NoResultFragment(query)
    }
}
