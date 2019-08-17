package io.github.vnicius.picpay.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.github.vnicius.picpay.R

/**
 * [Fragment] to show the spinner animation
 */
class SpinnerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spinner, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SpinnerFragment()
    }
}
