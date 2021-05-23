package com.example.physics_lab.ui.solved_works

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.physics_lab.R
import com.example.physics_lab.data.ActiveSolutionData
import com.example.physics_lab.data.Lab
import com.example.physics_lab.databinding.FragmentLabDescriptionBinding
import com.example.physics_lab.databinding.FragmentSolvedWorksBinding
import com.example.physics_lab.ui._base.BaseFragment
import com.example.physics_lab.ui._items.ActiveSolutionItem
import com.example.physics_lab.ui._items.LabDescrItem
import com.example.physics_lab.ui.lab_description.LabDescriptionViewModel
import com.example.physics_lab.ui.lab_description.LabDescriptionViewModelFactory
import com.google.android.material.button.MaterialButton
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class SolvedWorks : BaseFragment<FragmentSolvedWorksBinding>() {
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    lateinit var viewModel: SolvedWorksViewModel

    override fun getLayoutRes(): Int {
        return R.layout.fragment_solved_works
    }

    override fun provideViewModel() {
        viewModel =
            SolvedWorksViewModelFactory(requireContext()).create(SolvedWorksViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFields(view)
        setUpRecycler()
        loadInfoActiveSolution()
    }

    private fun observeFields(view: View) {
        var solutions: ArrayList<ActiveSolutionData.Solution>? = null
        viewModel.apiExceptionData.observe(viewLifecycleOwner, apiExceptionObserver)
        viewModel.activeSolution.observe(viewLifecycleOwner, { rawData ->
            adapter.clear()
            val emptyLabLayout = view.findViewById<LinearLayout>(R.id.emptySolvedWorksLayout)
            if (rawData.solutions.size != 0) {
                emptyLabLayout.visibility = View.INVISIBLE
                solutions = null
                solutions = ArrayList<ActiveSolutionData.Solution>()
                solutions!!.addAll(rawData.solutions)
                viewModel.loadTeacherClass()
            }
            else {
                emptyLabLayout.visibility = View.VISIBLE
            }
        })
        viewModel.classData.observe(viewLifecycleOwner, { classRoomItem ->
            solutions?.map { sol ->
                var name = classRoomItem.userClasses.find { it.id == sol.userId }?.name
                name = name ?: ""
                adapter.add(ActiveSolutionItem(sol, name))
            }

        })
    }

    private fun loadInfoActiveSolution() = viewModel.loadActiveSolution()

    private fun setUpRecycler() {
        viewBinding.recyclerViewSolvedWorks.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
    }


    private fun  setOnClick(view: View, lab: Lab) {

    }
}