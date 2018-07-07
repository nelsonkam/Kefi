package com.nelsonkam.kefi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.nelsonkam.kefi.models.Customer
import kotlinx.android.synthetic.main.activity_edit_customer.*

class EditCustomerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_customer)
        val id = intent.getStringExtra(CUSTOMER_ID)
        back_btn.setOnClickListener {
            finish()
        }
        add_btn.setOnClickListener {
            toggleLoading(true)
            val customer = Customer(id = id, name = name_input.text.toString(), phoneNumber = phone_input.text.toString())
            updateCustomer(customer, {
                toggleLoading(false)
                finish()
            }, {
                toggleLoading(false)
                Snackbar.make(window.decorView, "Une erreur est survenue", Snackbar.LENGTH_LONG).show()
            })
        }
        toggleLoading(true)
        fetchCustomer(id, { customer ->
            toggleLoading(false)
            name_input.setText(customer.name)
            phone_input.setText(customer.phoneNumber)
        }, {
            toggleLoading(false)
            Snackbar.make(window.decorView, "Une erreur est survenue", Snackbar.LENGTH_LONG).show()
        })

    }

    private fun toggleLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        add_btn.isEnabled = !isLoading
        name_input.isEnabled = !isLoading
        phone_input.isEnabled = !isLoading
    }
}
