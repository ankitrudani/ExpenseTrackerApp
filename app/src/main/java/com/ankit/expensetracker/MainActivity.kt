package com.ankit.expensetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout

class MainActivity : AppCompatActivity() {
    lateinit var toggle : ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.frameLayout,DashboardFragment()).commit()
        var drawer = findViewById<DrawerLayout>(R.id.drawer)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle = ActionBarDrawerToggle(this,drawer,R.string.open,R.string.close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        var lv = findViewById<ListView>(R.id.listview)
        var act = arrayOf("Daseboard","All Income","All Expense")
        var adapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,act)
        lv.adapter = adapter

        lv.setOnItemClickListener { adapterView, view, i, l ->
            drawer.closeDrawers()

            when(i){
                0 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout,DashboardFragment()).commit()
                }
                1 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout,AllIncomeFragment()).commit()
                }
                2 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout,AllExpenseFragment()).commit()
                }

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(1,11,1,"About")
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        if(item.itemId==11){
            startActivity(Intent(applicationContext,AboutActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }



}