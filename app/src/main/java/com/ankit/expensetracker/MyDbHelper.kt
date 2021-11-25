package com.ankit.expensetracker

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.ContactsContract
import android.widget.Toast
import java.lang.Exception
import java.sql.Array

class MyDbHelper(context: Context):
    SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object{
        private val DATABASE_NAME = "ExpenseDB"
        private val DATABASE_VERSION = 1
        val TRANSACTION_TABLE_NAME = "ALL_TRANSACTIONS"
        val COLUMN_TRANSACTION_ID = "transactionid"
        val COLUMN_TITLE = "title"
        val COLUMN_AMOUNT = "amount"
        val COLUMN_TRANSACTION_TYPE = "transaction_type"
        val COLUMN_TAG = "tag"
        val COLUMN_DATE = "date"
        val COLUMN_NOTE = "note"
        val COLUMN_CREATED_AT = "created_at"

    }
    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_TRANSACTION_TABLE = ("CREATE TABLE $TRANSACTION_TABLE_NAME ("+
                "$COLUMN_TRANSACTION_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "$COLUMN_TITLE TEXT,"+
                "$COLUMN_AMOUNT DOUBLE,"+
                "$COLUMN_TRANSACTION_TYPE TEXT,"+
                "$COLUMN_TAG TEXT,"+
                "$COLUMN_DATE TEXT,"+
                "$COLUMN_NOTE TEXT,"+
                "$COLUMN_CREATED_AT TEXT DEFAULT CURRENT_TIMESTAMP)")
        p0?.execSQL(CREATE_TRANSACTION_TABLE)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    open fun getTransaction(mCtx : Context) : ArrayList<Transaction>{
        val qry = "SELECT * FROM $TRANSACTION_TABLE_NAME ORDER BY $COLUMN_CREATED_AT DESC"
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry,null)
        val allTransaction = ArrayList<Transaction>()

        if(cursor.count==0){
            Toast.makeText(mCtx, "No Transaction Made",Toast.LENGTH_LONG).show()
        }
        else{
            while (cursor.moveToNext()){
                val transaction = Transaction()
                transaction.transactionID = cursor.getInt(0)
                transaction.transactionTitle = cursor.getString(1)
                transaction.transactionAmount = cursor.getDouble(2)
                transaction.transactionType = cursor.getString(3)
                transaction.transactionTag  =cursor.getString(4)
                transaction.transactionDate = cursor.getString(5)
                transaction.transactionNote = cursor.getString(6)
                allTransaction.add(transaction)
            }
        }
        return allTransaction
    }

    fun getTotalExpense(mCtx: Context) :ArrayList<Transaction>{
        val qry = "SELECT * FROM $TRANSACTION_TABLE_NAME WHERE $COLUMN_TRANSACTION_TYPE= 'Expense' ORDER BY $COLUMN_CREATED_AT DESC"
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry,null)
        val allExpense = ArrayList<Transaction>()

        if(cursor.count==0){
            Toast.makeText(mCtx, "No Transaction Made",Toast.LENGTH_LONG).show()
        }
        else{
            while (cursor.moveToNext()){
                val transaction = Transaction()
                transaction.transactionID = cursor.getInt(0)
                transaction.transactionTitle = cursor.getString(1)
                transaction.transactionAmount = cursor.getDouble(2)
                transaction.transactionType = cursor.getString(3)
                transaction.transactionTag  =cursor.getString(4)
                transaction.transactionDate = cursor.getString(5)
                transaction.transactionNote = cursor.getString(6)
                allExpense.add(transaction)
            }
        }
        return allExpense
    }

    fun getTotalIncome(mCtx: Context) :ArrayList<Transaction>{
        val qry = "SELECT * FROM $TRANSACTION_TABLE_NAME WHERE $COLUMN_TRANSACTION_TYPE= 'Income' ORDER BY $COLUMN_CREATED_AT DESC"
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry,null)
        val allIncome = ArrayList<Transaction>()

        if(cursor.count==0){
            Toast.makeText(mCtx, "No Transaction Made",Toast.LENGTH_LONG).show()
        }
        else{
            while (cursor.moveToNext()){
                val transaction = Transaction()
                transaction.transactionID = cursor.getInt(0)
                transaction.transactionTitle = cursor.getString(1)
                transaction.transactionAmount = cursor.getDouble(2)
                transaction.transactionType = cursor.getString(3)
                transaction.transactionTag  =cursor.getString(4)
                transaction.transactionDate = cursor.getString(5)
                transaction.transactionNote = cursor.getString(6)
                allIncome.add(transaction)
            }
        }
        return allIncome
    }

    fun getTransactionById(transactionId : Int) : Cursor{
        val qry = "SELECT * FROM $TRANSACTION_TABLE_NAME WHERE $COLUMN_TRANSACTION_ID = $transactionId"
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry,null)

        return cursor
    }

    fun addTransaction(transaction: Transaction) {
        var cv = ContentValues()
        var db = this.writableDatabase
        cv.put(COLUMN_TITLE, transaction.transactionTitle)
        cv.put(COLUMN_AMOUNT,transaction.transactionAmount)
        cv.put(COLUMN_TRANSACTION_TYPE,transaction.transactionType)
        cv.put(COLUMN_TAG,transaction.transactionTag)
        cv.put(COLUMN_DATE,transaction.transactionDate)
        cv.put(COLUMN_NOTE,transaction.transactionNote)
        db.insert(TRANSACTION_TABLE_NAME,null,cv)
    }

    fun getTotal(mCtx: Context) :Int{
        var total = 0
        total = getIncome() - getExpense()
        return total
    }

    fun getExpense() : Int{
        var expense = 0
        val qry = "SELECT sum($COLUMN_AMOUNT) FROM $TRANSACTION_TABLE_NAME WHERE $COLUMN_TRANSACTION_TYPE='Expense'"
        val db = this.readableDatabase
        val rs = db.rawQuery(qry,null)
        if(rs.moveToNext()){
            expense = rs.getInt(0)
        }
        return expense
    }

    fun getIncome() : Int{
        var income = 0
        val qry = "SELECT sum($COLUMN_AMOUNT) FROM $TRANSACTION_TABLE_NAME WHERE $COLUMN_TRANSACTION_TYPE='Income'"
        val db = this.readableDatabase
        val rs = db.rawQuery(qry,null)
        if(rs.moveToNext()){
            income = rs.getInt(0)
        }
        return income
    }

}