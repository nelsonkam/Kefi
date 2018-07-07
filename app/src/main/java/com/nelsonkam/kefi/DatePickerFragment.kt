package com.nelsonkam.kefi


import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.widget.DatePicker
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class DatePickerFragment() : DialogFragment(), DatePickerDialog.OnDateSetListener {
    interface OnDateSetListener {
        fun onDateSet(date: Calendar)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(activity!!, this, year, month, day)
    }

    override fun onDateSet(picker: DatePicker?, year: Int, month: Int, day: Int) {
        val callback: OnDateSetListener
        try {
            callback = activity as OnDateSetListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnDateSetListener ")
        }
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        callback.onDateSet(calendar)
    }

}// Required empty public constructor
