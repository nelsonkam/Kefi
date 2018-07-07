package com.nelsonkam.kefi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.nelsonkam.kefi.models.Customer
import kotlinx.android.synthetic.main.activity_create_customer.*

class CreateCustomerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_customer)
        toggleLoading(false)
        back_btn.setOnClickListener {
            finish()
        }
        add_btn.setOnClickListener {
            toggleLoading(true)
            saveCustomer(Customer(name_input.text.toString(), phone_input.text.toString()), {
                toggleLoading(false)
                finish()
            }, {
                toggleLoading(false)
                Snackbar.make(window.decorView, "Une erreur est survenue", Snackbar.LENGTH_LONG).show()
            })
        }
    }
    private fun toggleLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        add_btn.isEnabled = !isLoading
    }
}
