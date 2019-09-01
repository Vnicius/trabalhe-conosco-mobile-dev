package io.github.vnicius.picpay.ui.contacts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.github.vnicius.picpay.R
import io.github.vnicius.picpay.data.model.User
import io.github.vnicius.picpay.ui.common.adapters.ItemClick

class ContactsAdapter(val contacts: List<User>, val listener: ItemClick<User>): RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(contacts[position], listener)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        
        fun bindView(contact: User, listener: ItemClick<User>) {
            itemView.setOnClickListener { 
                listener.onClick(it, contact)
            }
            itemView.findViewById<TextView>(R.id.tv_contact_username).text = contact.username
            itemView.findViewById<TextView>(R.id.tv_contact_name).text = contact.name
            Picasso.get()
                .load(contact.img)
                .placeholder(R.drawable.avatar_placeholder)
                .into(itemView.findViewById<ImageView>(R.id.iv_contact_avatar))
        }
    }
}