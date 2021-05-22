package com.example.physics_lab.ui.lab_description

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.physics_lab.R
import com.example.physics_lab.data.Lab
import com.example.physics_lab.databinding.FragmentLabDescriptionBinding
import com.example.physics_lab.ui._base.BaseFragment
import com.example.physics_lab.ui._items.LabDescrItem
import com.google.android.material.button.MaterialButton
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder


class LabDescription : BaseFragment<FragmentLabDescriptionBinding>(){
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    lateinit var viewModel: LabDescriptionViewModel

    override fun getLayoutRes(): Int {
        return R.layout.fragment_lab_description
    }

    override fun provideViewModel() {
        viewModel =
            LabDescriptionViewModelFactory(requireContext()).create(LabDescriptionViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFields(view)
        setUpRecycler()
        loadInfoLabs()
    }

    private fun observeFields(view: View) {
        viewModel.apiExceptionData.observe(viewLifecycleOwner, apiExceptionObserver)
        viewModel.labDescrData.observe(viewLifecycleOwner, {
            adapter.clear()
            adapter.add(LabDescrItem("Какая тема?", it.task.theme))
            adapter.add(LabDescrItem("О чем?", it.task.description))
            adapter.add(LabDescrItem("Что используем?", it.task.equipment))
            adapter.notifyDataSetChanged()

            setOnClick(view, it)
        })
    }

    private fun loadInfoLabs() = viewModel.loadClassDescrStudent()

    private fun setUpRecycler() {
        viewBinding.recyclerViewLabDescr.let {
            it.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            it.adapter = adapter
        }
    }


    private fun  setOnClick(view: View, lab: Lab) {
        val button = view.findViewById<MaterialButton>(R.id.infoLabButton)
        if (lab.task.linkToManual != null) {
            button.setOnClickListener {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(lab.task.linkToManual)
                )
                startActivity(browserIntent)
            }
        }
    }
}