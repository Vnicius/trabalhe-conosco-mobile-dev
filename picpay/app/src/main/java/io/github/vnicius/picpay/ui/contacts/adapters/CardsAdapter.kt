package io.github.vnicius.picpay.ui.contacts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.vnicius.picpay.R
import io.github.vnicius.picpay.data.model.Card
import io.github.vnicius.picpay.ui.common.adapters.ItemClick

class CardsAdapter(val cards: List<Card>, val listener: ItemClick<Card>): RecyclerView.Adapter<CardsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(cards[position], listener)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindView(card: Card, listener: ItemClick<Card>) {
            itemView.setOnClickListener {
                listener.onClick(itemView, card)
            }
            itemView.findViewById<TextView>(R.id.tv_card_number).text = "Card ${card.number.subSequence(12, 16)}"
        }
    }
}