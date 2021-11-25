package com.ankit.expensetracker

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v =  inflater.inflate(R.layout.fragment_dashboard, container, false)
        var myDbHelper = MyDbHelper(requireContext())

        var totalBalance = v.findViewById<TextView>(R.id.total_balance)
        totalBalance.setText("₹"+myDbHelper.getTotal(requireContext()).toString())

        var totalExpense = v.findViewById<TextView>(R.id.expense_total)
        totalExpense.setText("-₹"+myDbHelper.getExpense().toString())

        var totalIncome = v.findViewById<TextView>(R.id.income_total)
        totalIncome.setText("+₹"+myDbHelper.getIncome().toString())

        var an = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_in_left)

        var incomeCv = v.findViewById<CardView>(R.id.income_cv)
        var expenseCv = v.findViewById<CardView>(R.id.expense_cv)
        var rv = v.findViewById<RecyclerView>(R.id.transaction_rv)
        var tv = v.findViewById<TextView>(R.id.text_recent_transaction)

        var dashboard = v.findViewById<LinearLayout>(R.id.dashboard)
        incomeCv.startAnimation(an)
        expenseCv.startAnimation(an)
        rv.startAnimation(an)
        tv.startAnimation(an)
        var emptySV = v.findViewById<RelativeLayout>(R.id.empty_state_view)
        if(myDbHelper.getIncome()==0 && myDbHelper.getExpense()==0){
            dashboard.isVisible = false
            emptySV.isVisible = true
        }

        var allTransaction  = myDbHelper.getTransaction(requireContext())
        var adapter = TransactionAdapter(requireContext(),allTransaction)


        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        var addtransaction = v.findViewById<FloatingActionButton>(R.id.addTransactionBU)
        addtransaction.setOnClickListener {
            startActivity(Intent(requireContext(),AddTransactionActivity::class.java))
        }

        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DashboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}