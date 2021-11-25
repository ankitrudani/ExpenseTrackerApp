package com.ankit.expensetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TransactionDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_details)

        var transactionId = intent.getIntExtra("transactionId",0)

        var title = findViewById<TextView>(R.id.title)
        var amount = findViewById<TextView>(R.id.amount)
        var type = findViewById<TextView>(R.id.type)
        var tag = findViewById<TextView>(R.id.tag)
        var date = findViewById<TextView>(R.id.date)
        var note = findViewById<TextView>(R.id.note)
        var createdAt = findViewById<TextView>(R.id.createdAt)

        var editBtn = findViewById<FloatingActionButton>(R.id.editTransactionBtn)

        var helper = MyDbHelper(applicationContext)
        var rs = helper.getTransactionById(transactionId)

        var tid = 0
        if(rs.moveToNext()){
            tid = rs.getInt(0)
            title.setText(rs.getString(1))
            amount.setText(rs.getString(2))
            type.setText(rs.getString(3))
            tag.setText(rs.getString(4))
            date.setText(rs.getString(5))
            note.setText(rs.getString(6))
            createdAt.setText(rs.getString(7))
        }
        editBtn.setOnClickListener {
            var intent = Intent(applicationContext,EditTransactionActivity::class.java)
            intent.putExtra("transactionId",tid)
            startActivity(intent)
        }
    }

}
