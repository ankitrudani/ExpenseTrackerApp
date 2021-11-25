package com.ankit.expensetracker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class AddTransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        var saveTrans = findViewById<MaterialButton>(R.id.btn_save_transaction)
        var title = findViewById<TextInputEditText>(R.id.et_title)
        var amount = findViewById<TextInputEditText>(R.id.et_amount)
        var transactionType = findViewById<AutoCompleteTextView>(R.id.et_transactionType)
        var type = arrayOf("Income","Expense")
        var adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,type)
        transactionType.setAdapter(adapter)

        var transactionTag = findViewById<AutoCompleteTextView>(R.id.et_tag)
        var tags = arrayOf("Food","Housing","Transportation","Utilities","Healthcare","Personal","Entertainment","Other")
        var adapter2 = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,tags)
        transactionTag.setAdapter(adapter2)

        var date = findViewById<TextInputEditText>(R.id.et_when)

        date.setOnClickListener {
            var c = Calendar.getInstance()
            DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                var dt = "$i-${i2-1}-$i3"
                date.setText(dt)
            },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show()
        }

        var note = findViewById<TextInputEditText>(R.id.et_note)

        saveTrans.setOnClickListener {
            var transaction = Transaction()
            transaction.transactionTitle = title.text.toString()
            transaction.transactionAmount = amount.text.toString().toDouble()
            transaction.transactionType = transactionType.text.toString()
            transaction.transactionTag = transactionTag.text.toString()
            transaction.transactionDate = date.text.toString()
            transaction.transactionNote = note.text.toString()
            var myDbHelper = MyDbHelper(this)
            myDbHelper.addTransaction(transaction)

            startActivity(Intent(this,MainActivity::class.java))
            this.finish()
        }
    }
}