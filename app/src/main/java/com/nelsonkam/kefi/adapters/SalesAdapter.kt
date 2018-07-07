package com.nelsonkam.kefi.adapters

import com.ahamed.multiviewadapter.DataListManager
import com.ahamed.multiviewadapter.RecyclerAdapter

import com.nelsonkam.kefi.binders.SalesBinder
import com.nelsonkam.kefi.models.Sales

class SalesAdapter(onClick: (Sales) -> Unit = {}) : RecyclerAdapter() {

    private val salesListManager: DataListManager<Sales>

    init {
        this.salesListManager = DataListManager(this)
        addDataManager(salesListManager)

        registerBinder(SalesBinder(onClick))
    }

    fun setSalesList(dataList: List<Sales>) {
        salesListManager.set(dataList)
    }
}