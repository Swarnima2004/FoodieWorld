package com.example.foodieworld.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodieworld.Adapter.BuyAgainAdapter
import com.example.foodieworld.R
import com.example.foodieworld.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(layoutInflater,container,false)
        setUpRecyclerView()
        return binding.root
    }

    private fun setUpRecyclerView(){
        val buyAgainFoodName = listOf("burger","momos","Rasmalai","paneer tikka","coke")
        val buyAgainItemPrice = listOf("$4","$7","$98","$31","$9")
        val buyAgainImages = listOf(R.drawable.burgerfood,R.drawable.gulab_jamun,R.drawable.paneertikka, R.drawable.ras, R.drawable.food)
        buyAgainAdapter = BuyAgainAdapter(ArrayList(buyAgainFoodName),ArrayList(buyAgainItemPrice),ArrayList(buyAgainImages))
        binding.RecyclerViewBuyAgain.adapter = buyAgainAdapter
        binding.RecyclerViewBuyAgain.layoutManager = LinearLayoutManager(requireContext())
    }

    companion object {

    }
}