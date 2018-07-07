package com.nelsonkam.kefi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.nelsonkam.kefi.adapters.CustomerAdapter
import com.nelsonkam.kefi.models.Customer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var customersAdapter: CustomerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        customersAdapter = CustomerAdapter(this::onCustomerClicked)
        customers_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = customersAdapter
        }
        floatingActionButton.setOnClickListener {
            startActivity(Intent(this, CreateCustomerActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        toggleLoading(true)
        fetchCustomers(this::onCustomersFetched, this::onError)
    }
    private fun onCustomerClicked(customer: Customer) {
        val intent = Intent(this, CustomerActivity::class.java)
        intent.putExtra(CUSTOMER_ID, customer.id)
        intent.putExtra(CUSTOMER_NAME, customer.name)
        startActivity(intent)
    }
    private fun onCustomersFetched(customers: List<Customer>) {
        toggleLoading(false)
        if (customers.isEmpty()) {
            customers_list.visibility = View.GONE
            empty_text.visibility = View.VISIBLE
            return
        }
        empty_text.visibility = View.GONE
        customers_list.visibility = View.VISIBLE
        customersAdapter.setCustomerList(customers)

    }
    private fun onError(e: Exception) {
        toggleLoading(false)
        Snackbar.make(window.decorView, "Une erreur est survenue", Snackbar.LENGTH_LONG).show()
    }
    private fun toggleLoading(isLoading: Boolean) {
        ma_progress.visibility = if (isLoading) View.VISIBLE else View.GONE
        if (isLoading) {
            customers_list.visibility =  View.GONE
            empty_text.visibility = View.GONE
        }
    }
}
