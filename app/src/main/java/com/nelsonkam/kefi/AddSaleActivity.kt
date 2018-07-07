package com.nelsonkam.kefi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.nelsonkam.kefi.models.Sales
import kotlinx.android.synthetic.main.activity_add_sale.*
import java.text.SimpleDateFormat
import java.util.*

class AddSaleActivity : AppCompatActivity(), DatePickerFragment.OnDateSetListener {

    private var salesDate: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sale)
        toggleLoading(false)
        val fragment = DatePickerFragment()
        val id = intent.getStringExtra(CUSTOMER_ID)
        date_input.isCursorVisible = false
        date_input.isFocusableInTouchMode = false
        date_input.isFocusable = false
        date_input.setOnClickListener {
            fragment.show(supportFragmentManager, "datePicker")
        }
        back_btn.setOnClickListener {
            finish()
        }
        add_btn.setOnClickListener {
            toggleLoading(true)
            val sale = Sales(
                    product = product_name_input.text.toString(),
                    quantity = quantity.text.toString().toInt(),
                    unitPrice = unit_price_input.text.toString().toInt(),
                    date = salesDate.timeInMillis
            )
            saveSale(id, sale, {
                toggleLoading(false)
                finish()
            }, {
                toggleLoading(false)
                Snackbar.make(window.decorView, "Une erreur est survenue", Snackbar.LENGTH_LONG).show()
            })
        }
    }

    override fun onDateSet(date: Calendar) {
        val formatter = SimpleDateFormat.getDateInstance();
        date_input.setText(formatter.format( Date(date.timeInMillis)))
        salesDate = date
    }
    private fun toggleLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        add_btn.isEnabled = !isLoading

    }
}
