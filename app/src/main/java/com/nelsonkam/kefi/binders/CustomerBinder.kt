package com.nelsonkam.kefi.binders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.ahamed.multiviewadapter.BaseViewHolder
import com.ahamed.multiviewadapter.ItemBinder
import com.nelsonkam.kefi.R
import com.nelsonkam.kefi.models.Customer

class CustomerBinder(private val onClick: (Customer) -> Unit) : ItemBinder<Customer, CustomerBinder.ViewHolder>() {

    override fun create(layoutInflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.customer_item, parent, false), onClick)
    }

    override fun canBindData(item: Any): Boolean {
        return item is Customer
    }

    override fun bind(holder: ViewHolder, item: Customer) {
        holder.name.text = item.name
        holder.phoneNumber.text = item.phoneNumber
    }

    class ViewHolder(itemView: View, onClick: (Customer) -> Unit) : BaseViewHolder<Customer>(itemView) {
        val name: TextView
        val phoneNumber: TextView

        init {
            name = itemView.findViewById(R.id.name)
            phoneNumber = itemView.findViewById(R.id.phone_number)
            setItemClickListener { view, item ->
                onClick(item)
            }
        }
    }
}