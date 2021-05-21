package com.example.physics_lab.ui.lab_list.finish_lab

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
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
        observeFields(view)
        loadActiveFinishData()
    }

    private fun observeFields(view: View) {
        viewModel.apiExceptionData.observe(viewLifecycleOwner, apiExceptionObserver)
        viewModel.activeFinishData.observe(viewLifecycleOwner, { rawData ->
            adapter.clear()
            rawData.finishedSolutions.map {
                LabListItem(it.lab)
            }
            adapter.notifyDataSetChanged()
            val emptyLabLayout = view.findViewById<LinearLayout>(R.id.emptyFinishLabLayout)
            if (adapter.itemCount == 0) {
                emptyLabLayout.visibility = View.VISIBLE
            }
            else {
                emptyLabLayout.visibility = View.INVISIBLE
            }
        })
    }

    private fun setUpRecycler() {
        viewBinding.recyclerViewStudFinishLab.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
    }

    private fun  loadActiveFinishData() = viewModel.loadActiveFinishLab()

}