package com.example.physics_lab.ui.statistic_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.physics_lab.R
import com.example.physics_lab.databinding.FragmentLabListBinding
import com.example.physics_lab.databinding.FragmentStartScreenBinding
import com.example.physics_lab.databinding.FragmentStatisticScreenBinding
import com.example.physics_lab.ui._base.BaseFragment
import com.example.physics_lab.ui.lab_list.LabListViewModel
import com.example.physics_lab.ui.lab_list.LabListViewModelFactory
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class StatisticScreen : BaseFragment<FragmentStatisticScreenBinding>(){
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }
    lateinit var viewModel: StatisticScreenViewModel

    override fun getLayoutRes(): Int {
        return R.layout.fragment_statistic_screen
    }

    override fun provideViewModel() {
        viewModel =
            StatisticScreenViewModelFactory(requireContext()).create(StatisticScreenViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
//        observeFields(view)
//        loadClass()
//        initButtons(view)
//        setOnClick()
    }

    private fun setUpRecycler() {
        viewBinding.recyclerViewStatistic.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
    }
}