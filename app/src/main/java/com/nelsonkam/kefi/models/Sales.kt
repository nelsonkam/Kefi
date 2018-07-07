package com.nelsonkam.kefi.models

/**
 * Created by nelson on 5/6/18.
 */

data class Sales(var id: String = "", val product: String = "", val quantity: Int = 0, val unitPrice: Int = 0, val date: Long = System.currentTimeMillis())