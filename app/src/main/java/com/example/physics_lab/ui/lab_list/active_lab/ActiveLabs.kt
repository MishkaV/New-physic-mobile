package com.example.physics_lab.ui.lab_list.active_lab

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.physics_lab.R
import com.example.physics_lab.data.ActiveFinishData
import com.example.physics_lab.databinding.FragmentActiveLabsBinding
import com.example.physics_lab.databinding.FragmentFinishLabBinding
import com.example.physics_lab.ui._base.BaseFragment
import com.example.physics_lab.ui._items.LabListItem
import com.example.physics_lab.ui.lab_list.finish_lab.FinishLabViewModel
import com.example.physics_lab.ui.lab_list.finish_lab.FinishLabViewModelFactory
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder


class ActiveLabs  : BaseFragment<FragmentActiveLabsBinding>() {
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }
    lateinit var viewModel: ActiveLabsViewModel

    override fun getLayoutRes(): Int {
        return  R.layout.fragment_active_labs
    }

    override fun provideViewModel() {
        viewModel =
            ActiveLabsViewModelFactory(requireContext()).create(ActiveLabsViewModel::class.java)
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
            rawData.activeSolutions.map {
                adapter.add(LabListItem(it.lab))
            }
            adapter.notifyDataSetChanged()
            val emptyLabLayout = view.findViewById<LinearLayout>(R.id.emptyActiveLabLayout)
            if (adapter.itemCount == 0) {
                emptyLabLayout.visibility = View.VISIBLE
            }
            else {
                emptyLabLayout.visibility = View.INVISIBLE
            }
        })
    }

    private fun setUpRecycler() {
        viewBinding.recyclerViewStudActivehLab.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
    }

    private fun  loadActiveFinishData() = viewModel.loadActiveFinishLab()

}

