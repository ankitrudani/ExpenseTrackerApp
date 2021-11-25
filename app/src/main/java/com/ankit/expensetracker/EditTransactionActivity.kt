package com.ankit.expensetracker

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class EditTransactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_transaction)

        var transactionId = intent.getIntExtra("transactionId",0)

        var saveTrans = findViewById<MaterialButton>(R.id.btn_update_transaction)
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
        var helper = MyDbHelper(applicationContext)
        var rs = helper.getTransactionById(transactionId)

        if(rs.moveToNext()){
            title.setText(rs.getString(1))
            amount.setText(rs.getString(2))
            transactionType.setText(rs.getString(3),false)
            transactionTag.setText(rs.getString(4),false)
            date.setText(rs.getString(5))
            note.setText(rs.getString(6))
        }

    }
}