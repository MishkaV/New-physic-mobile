package com.example.physics_lab.ui.solved_works

import android.content.Context
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
import com.example.physics_lab.service.ClassService
import com.example.physics_lab.service.SolutionService
import com.example.physics_lab.ui._base.BaseFragment
import com.example.physics_lab.ui._items.ActiveSolutionItem
import com.example.physics_lab.ui._items.LabDescrItem
import com.example.physics_lab.ui._items.LabListItem
import com.example.physics_lab.ui.lab_description.LabDescriptionViewModel
import com.example.physics_lab.ui.lab_description.LabDescriptionViewModelFactory
import com.getbase.floatingactionbutton.FloatingActionButton
import com.google.android.material.button.MaterialButton
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import java.text.SimpleDateFormat

class SolvedWorks : BaseFragment<FragmentSolvedWorksBinding>() {
    lateinit var solutionService: SolutionService
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    lateinit var viewModel: SolvedWorksViewModel

    override fun getLayoutRes(): Int {
        return R.layout.fragment_solved_works
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        solutionService = SolutionService(context)
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
        setOnClick(view)
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
        viewModel.removeResponse.observe(viewLifecycleOwner, {
            navController.popBackStack()
        })
    }

    private fun loadInfoActiveSolution() = viewModel.loadActiveSolution()

    private fun setUpRecycler() {
        viewBinding.recyclerViewSolvedWorks.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
    }


    private fun  setOnClick(view: View) {
        adapter.setOnItemClickListener { item, view ->
            val solItem = item as ActiveSolutionItem
            solutionService.saveName(solItem.name)
            solutionService.saveCheckedUserId(solItem.item.userId.toString())
            solutionService.saveDateOfDownload(getDate(solItem.item.dateOfDownload))
            solutionService.saveResult(solItem.item.solution)
            solutionService.saveVideoPath(solItem.item.videoPath)
            navController.navigate(R.id.action_solvedWorksFragment_to_setMarkFragment)
        }

        val deleteLab = view.findViewById<FloatingActionButton>(R.id.deleteLabButton)
        deleteLab.setOnClickListener {
            viewModel.deleteLab()
        }
    }

    fun getDate(dateStr: String) : String{
        try {
            val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("dd MMMM yyyy")
            val formattedDate = formatter.format(parser.parse(dateStr))
            return formattedDate.toString()
        } catch (e: Exception){
            return ""
        }
    }
}