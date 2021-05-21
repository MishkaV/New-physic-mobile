package com.example.physics_lab.ui.lab_list.finish_lab

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.physics_lab.R
import com.example.physics_lab.data.ActiveFinishData
import com.example.physics_lab.databinding.FragmentFinishLabBinding
import com.example.physics_lab.ui._base.BaseFragment
import com.example.physics_lab.ui._items.LabListItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class FinishLab : BaseFragment<FragmentFinishLabBinding>() {
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }
    lateinit var viewModel: FinishLabViewModel
    override fun getLayoutRes(): Int {
        return  R.layout.fragment_finish_lab
    }

    override fun provideViewModel() {
        viewModel =
            FinishLabViewModelFactory(requireContext()).create(FinishLabViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        observeFields()
        loadActiveFinishData()
    }

    private fun observeFields() {
        viewModel.apiExceptionData.observe(viewLifecycleOwner, apiExceptionObserver)
        viewModel.activeFinishData.observe(viewLifecycleOwner, activeFinishDatObserver)
    }

    private fun setUpRecycler() {
        viewBinding.recyclerViewStudFinishLab.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
    }

    private fun  loadActiveFinishData() = viewModel.loadActiveFinishLab()

    private val activeFinishDatObserver = Observer<ActiveFinishData> { rawData ->
        adapter.clear()
        rawData.finishedSolutions.map {
            LabListItem(it.lab)
        }
        adapter.notifyDataSetChanged()
    }
}