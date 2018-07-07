package com.nelsonkam.kefi.binders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.ahamed.multiviewadapter.BaseViewHolder
import com.ahamed.multiviewadapter.ItemBinder
import com.nelsonkam.kefi.R
import com.nelsonkam.kefi.models.Sales
import java.text.SimpleDateFormat
import java.util.*

class SalesBinder(private val onClick: (Sales) -> Unit) : ItemBinder<Sales, SalesBinder.ViewHolder>() {

    override fun create(layoutInflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.purchase_item, parent, false), onClick)
    }

    override fun canBindData(item: Any): Boolean {
        return item is Sales
    }

    override fun bind(holder: ViewHolder, item: Sales) {
        holder.product.text = item.product
        val formatter = SimpleDateFormat.getDateInstance();
        holder.date.text = formatter.format( Date(item.date))
        holder.total.text = (item.unitPrice * item.quantity).toString() + "F"
        holder.details.text = "Prix U.: ${item.unitPrice}F Quantité: ${item.quantity}"
    }

    class ViewHolder(itemView: View, onClick: (Sales) -> Unit) : BaseViewHolder<Sales>(itemView) {
        val product: TextView = itemView.findViewById(R.id.product_name)
        val date: TextView = itemView.findViewById(R.id.purchase_date)
        val details: TextView = itemView.findViewById(R.id.sales_details)
        val total: TextView = itemView.findViewById(R.id.total)
        init {
            setItemClickListener { view, item ->
                onClick(item)
            }
        }
    }
}