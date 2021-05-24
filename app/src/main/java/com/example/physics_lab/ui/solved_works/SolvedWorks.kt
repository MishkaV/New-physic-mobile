package com.example.physics_lab.ui.solved_works

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
        val emptyLabLayout = view.findViewById<LinearLayout>(R.id.emptySolvedWorksLayout)
        viewModel.apiExceptionData.observe(viewLifecycleOwner, apiExceptionObserver)
        viewModel.activeSolution.observe(viewLifecycleOwner, { rawData ->
            adapter.clear()
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

                if (classRoomItem.users != null) {
                    var name = classRoomItem.users.find { it.id == sol.userId }?.name
                    name = name ?: ""
                    adapter.add(ActiveSolutionItem(sol, name))

                    if(adapter.itemCount == 0)
                        emptyLabLayout.visibility = View.VISIBLE
                    else
                        emptyLabLayout.visibility = View.INVISIBLE
                }
                else {
                    emptyLabLayout.visibility = View.VISIBLE
                }
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
//            Log.d("TAG", solItem.name.toString())
            if (solItem.item.dateOfDownload != null)
                solutionService.saveDateOfDownload(getDate(solItem.item.dateOfDownload))
            else
                solutionService.saveDateOfDownload("Отсутствует")

            if (solItem.item.solution!= null)
                solutionService.saveResult(solItem.item.solution)
            else
                solutionService.saveResult("Отсутствует")

            if (solItem.item.videoPath != null)
                solutionService.saveVideoPath(solItem.item.videoPath)
            else
                solutionService.saveVideoPath("")

            navController.navigate(R.id.action_solvedWorksFragment_to_setMarkFragment)
        }

        val deleteLab = view.findViewById<FloatingActionButton>(R.id.deleteLabButton)
        deleteLab.setOnClickListener {
            viewModel.deleteLab()
        }
    }

    fun getDate(dateStr: String?) : String{
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