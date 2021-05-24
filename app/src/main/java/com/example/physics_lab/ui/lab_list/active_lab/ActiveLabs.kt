package com.example.physics_lab.ui.lab_list.active_lab

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.physics_lab.R
import com.example.physics_lab.databinding.FragmentActiveLabsBinding
import com.example.physics_lab.service.LabService
import com.example.physics_lab.ui._base.BaseFragment
import com.example.physics_lab.ui._items.LabListItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder


class ActiveLabs  : BaseFragment<FragmentActiveLabsBinding>() {
    lateinit var labService: LabService
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        labService = LabService(context)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        observeFields(view)
        loadActiveFinishData()
        setOnClick()
    }

    private fun observeFields(view: View) {
        viewModel.apiExceptionData.observe(viewLifecycleOwner, apiExceptionObserver)
        viewModel.activeFinishData.observe(viewLifecycleOwner, { rawData ->
            adapter.clear()
            rawData.activeSolutions.map {
                adapter.add(LabListItem(it.lab, "active_labs"))
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

    private fun setOnClick() {
        adapter.setOnItemClickListener { item, view ->
            val labItem = item as LabListItem
            labService.saveLabId(labItem.item.id.toString())
            navController.navigate(R.id.action_activeLabsFragment_to_labDescriptionFragment)
        }
    }
}

