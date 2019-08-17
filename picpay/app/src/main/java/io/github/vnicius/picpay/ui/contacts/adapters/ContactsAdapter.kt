package io.github.vnicius.picpay.ui.contacts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.github.vnicius.picpay.R
import io.github.vnicius.picpay.data.model.Contact
import io.github.vnicius.picpay.ui.common.adapters.ItemClick

class ContactsAdapter(val contacts: List<Contact>, val listener: ItemClick<Contact>): RecyclerView.Adapter<ContactsAdapter.VielHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VielHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)

        return VielHolder(
            view,
            object : OnItemClickListener {
                override fun onClick(view: View, position: Int) {
                    listener.onClick(view, contacts[position])
                }
            })
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: VielHolder, position: Int) {
        holder.bindView(contacts[position])
    }

    interface OnItemClickListener {
        fun onClick(view: View, position: Int)
    }

    class VielHolder(itemvView: View, private val listener: OnItemClickListener): RecyclerView.ViewHolder(itemvView), View.OnClickListener {

        init {
            itemvView.setOnClickListener(this)
        }

        override fun onClick(view: View) {

            listener.onClick(view, adapterPosition)
        }


        fun bindView(contact: Contact) {
            itemView.findViewById<TextView>(R.id.tv_contact_username).text = contact.username
            itemView.findViewById<TextView>(R.id.tv_contact_name).text = contact.name
            Picasso.get()
                .load(contact.img)
                .placeholder(R.drawable.avatar_placeholder)
                .into(itemView.findViewById<ImageView>(R.id.iv_contact_avatar))
        }
    }
}