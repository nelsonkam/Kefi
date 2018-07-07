package com.nelsonkam.kefi.adapters

import com.ahamed.multiviewadapter.DataListManager
import com.ahamed.multiviewadapter.RecyclerAdapter
import com.nelsonkam.kefi.binders.CustomerBinder

import com.nelsonkam.kefi.models.Customer

class CustomerAdapter(onClick: (Customer) -> Unit) : RecyclerAdapter() {

    private val customerListManager: DataListManager<Customer>

    init {
        this.customerListManager = DataListManager(this)
        addDataManager(customerListManager)

        registerBinder(CustomerBinder(onClick))
    }

    fun setCustomerList(dataList: List<Customer>) {
        customerListManager.set(dataList)
    }
}