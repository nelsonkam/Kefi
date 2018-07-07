package com.nelsonkam.kefi

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.nelsonkam.kefi.models.Sales
import kotlinx.android.synthetic.main.activity_edit_sale.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton
import java.text.SimpleDateFormat
import java.util.*

class EditSaleActivity : AppCompatActivity(), DatePickerFragment.OnDateSetListener {
    private var salesDate: Calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_sale)
        toggleLoading(false)
        val fragment = DatePickerFragment()
        val customerID = intent.getStringExtra(CUSTOMER_ID)
        val id = intent.getStringExtra(SALE_ID)
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
                    id = id,
                    product = product_name_input.text.toString(),
                    quantity = quantity.text.toString().toInt(),
                    unitPrice = unit_price_input.text.toString().toInt(),
                    date = salesDate.timeInMillis
            )
            updateSale(customerID, sale, {
                toggleLoading(false)
                finish()
            }, {
                toggleLoading(false)
                Snackbar.make(window.decorView, "Une erreur est survenue", Snackbar.LENGTH_LONG).show()
            })
        }

        toggleLoading(true)
        fetchSale(id, customerID, { sale ->
            toggleLoading(false)
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = sale.date
            onDateSet(calendar)
            product_name_input.setText(sale.product)
            quantity.setText(sale.quantity.toString())
            unit_price_input.setText(sale.unitPrice.toString())
        }, {
            toggleLoading(false)
            Snackbar.make(window.decorView, "Une erreur est survenue", Snackbar.LENGTH_LONG).show()
        })
        delete_btn.setOnClickListener {
            alert("Êtes-vous sûr de vouloir supprimer cette vente et toutes ses informations ?","Suppression de la vente" ) {
                yesButton { dialog ->
                    deleteSale(id, customerID, {
                        dialog.dismiss()
                        finish()
                    }, {
                        Snackbar.make(window.decorView, "Une erreur est survenue lors de la suppression.", Snackbar.LENGTH_LONG).show()
                    })
                }
                noButton {}
            }.show()
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
        product_name_input.isEnabled = !isLoading
        quantity.isEnabled = !isLoading
        unit_price_input.isEnabled = !isLoading
    }
}
