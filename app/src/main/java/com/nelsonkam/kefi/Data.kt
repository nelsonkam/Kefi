package com.nelsonkam.kefi

import com.google.firebase.firestore.FirebaseFirestore
import com.nelsonkam.kefi.models.Customer
import com.nelsonkam.kefi.models.Sales

/**
 * Created by nelson on 3/6/18.
 */
fun saveCustomer(customer: Customer, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val doc = db.collection("customers").document()
    customer.id = doc.id
    doc.set(customer).addOnSuccessListener {
        onSuccess()
    }.addOnFailureListener(onError)
}
fun updateCustomer(customer: Customer, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val doc = db.collection("customers").document(customer.id)
    doc.set(customer).addOnSuccessListener {
        onSuccess()
    }.addOnFailureListener(onError)
}
fun deleteCustomer(id: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val doc = db.collection("customers").document(id)
    doc.delete().addOnSuccessListener {
        onSuccess()
    }.addOnFailureListener(onError)
}
fun fetchCustomers(onSuccess: (List<Customer>) -> Unit, onError: (Exception) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("customers").get().addOnSuccessListener { querySnapshot ->
        val customers = querySnapshot.toObjects(Customer::class.java)
        onSuccess(customers)
    }.addOnFailureListener(onError)
}

fun fetchSales(id: String, onSuccess: (List<Sales>) -> Unit, onError: (Exception) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("customers").document(id).collection("sales").get().addOnSuccessListener { querySnapshot ->
        val sales = querySnapshot.toObjects(Sales::class.java)
        onSuccess(sales)
    }.addOnFailureListener(onError)
}

fun saveSale(customerID: String, sale: Sales, onSuccess: () -> Unit, onError: (Exception) -> Unit)  {
    val db = FirebaseFirestore.getInstance()
    val doc = db.collection("customers").document(customerID).collection("sales").document()
    sale.id = doc.id
    doc.set(sale).addOnSuccessListener {
        onSuccess()
    }.addOnFailureListener(onError)
}
fun fetchCustomer(id: String, onSuccess: (Customer) -> Unit, onError: (Exception) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("customers").document(id).get().addOnSuccessListener { querySnapshot ->
        if (querySnapshot.exists()) {
            val customer = querySnapshot.toObject(Customer::class.java)
            onSuccess(customer!!)
        } else {
            onError(Exception("No document with that id: $id was found."))
        }
    }.addOnFailureListener(onError)
}

fun fetchSale(id: String, customerID: String, onSuccess: (Sales) -> Unit, onError: (Exception) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("customers")
            .document(customerID).collection("sales")
            .document(id)
            .get().addOnSuccessListener { querySnapshot ->
        if (querySnapshot.exists()) {
            val sale = querySnapshot.toObject(Sales::class.java)
            onSuccess(sale!!)
        } else {
            onError(Exception("No document with that id: $id was found."))
        }
    }.addOnFailureListener(onError)
}
fun updateSale(customerID: String, sale: Sales, onSuccess: () -> Unit, onError: (Exception) -> Unit)  {
    val db = FirebaseFirestore.getInstance()
    val doc = db.collection("customers")
            .document(customerID)
            .collection("sales")
            .document(sale.id)
    doc.set(sale).addOnSuccessListener {
        onSuccess()
    }.addOnFailureListener(onError)
}
fun deleteSale(id: String, customerID: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val doc = db.collection("customers").document(customerID)
            .collection("sales")
            .document(id)
    doc.delete().addOnSuccessListener {
        onSuccess()
    }.addOnFailureListener(onError)
}