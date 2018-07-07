package com.nelsonkam.kefi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.nelsonkam.kefi.adapters.SalesAdapter
import com.nelsonkam.kefi.models.Customer
import com.nelsonkam.kefi.models.Sales
import kotlinx.android.synthetic.main.activity_customer.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton

class CustomerActivity : AppCompatActivity() {

    private lateinit var salesAdapter: SalesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)
        salesAdapter = SalesAdapter(this::onSaleClicked)
//        customer_name.text = intent.getStringExtra(CUSTOMER_NAME)
        sales_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@CustomerActivity)
            adapter = salesAdapter

        }
        back_btn.setOnClickListener {
            finish()
        }
        edit_btn.setOnClickListener {
            val id = intent.getStringExtra(CUSTOMER_ID)
            val intent = Intent(this, EditCustomerActivity::class.java)
            intent.putExtra(CUSTOMER_ID, id)
            startActivity(intent)
        }
        delete_btn.setOnClickListener {
            val id = intent.getStringExtra(CUSTOMER_ID)
            alert("Êtes-vous sûr de vouloir supprimer ce contact et toutes ses informations ?","Suppression du contact" ) {
                yesButton { dialog ->
                    deleteCustomer(id, {
                        dialog.dismiss()
                        finish()
                    }, {
                        Snackbar.make(window.decorView, "Une erreur est survenue lors de la suppression.", Snackbar.LENGTH_LONG).show()
                    })
                }
                noButton {}
            }.show()
        }
        floatingActionButton.setOnClickListener {
            val id = intent.getStringExtra(CUSTOMER_ID)
            val intent = Intent(this, AddSaleActivity::class.java)
            intent.putExtra(CUSTOMER_ID, id)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val id = intent.getStringExtra(CUSTOMER_ID)
        toggleLoading(true)
        fetchSales(id, this::onSalesFetched, this::onError)
    }
    private fun onSaleClicked(sale: Sales) {
        startActivity<EditSaleActivity>(CUSTOMER_ID to intent.getStringExtra(CUSTOMER_ID), SALE_ID to sale.id)
    }

    private fun onSalesFetched(sales: List<Sales>) {
        toggleLoading(false)
        if (sales.isEmpty()) {
            sales_list.visibility = View.GONE
            customer_empty.visibility = View.VISIBLE
            return
        }
        customer_empty.visibility = View.GONE
        sales_list.visibility = View.VISIBLE
        salesAdapter.setSalesList(sales)

    }
    private fun onError(e: Exception) {
        toggleLoading(false)
        Snackbar.make(window.decorView, "Une erreur est survenue", Snackbar.LENGTH_LONG).show()
    }
    private fun toggleLoading(isLoading: Boolean) {
        ca_progress.visibility = if (isLoading) View.VISIBLE else View.GONE
        if (isLoading) {
            sales_list.visibility =  View.GONE
            customer_empty.visibility = View.GONE
        }
    }
}
