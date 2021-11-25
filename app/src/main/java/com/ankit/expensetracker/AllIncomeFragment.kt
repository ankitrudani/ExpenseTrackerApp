package com.ankit.expensetracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AllIncomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AllIncomeFragment : Fragment() {
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
        var v = inflater.inflate(R.layout.fragment_all_income, container, false)

        var myDbHelper = MyDbHelper(requireContext())

        var totalIncome = v.findViewById<TextView>(R.id.total_income)
        totalIncome.setText("+₹"+myDbHelper.getIncome().toString())


        var emptySV = v.findViewById<RelativeLayout>(R.id.empty_state_view)
        var ll = v.findViewById<LinearLayout>(R.id.allIncome)
        if(myDbHelper.getIncome()==0){
            ll.isVisible = false
            emptySV.isVisible = true
        }

        var rv= v.findViewById<RecyclerView>(R.id.all_income_rv)
        var allExpense = myDbHelper.getTotalIncome(requireContext())
        var adapter = TransactionAdapter(requireContext(),allExpense)

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter
        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AllIncomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AllIncomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}