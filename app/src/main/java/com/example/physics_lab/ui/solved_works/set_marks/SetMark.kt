package com.example.physics_lab.ui.solved_works.set_marks

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.physics_lab.R
import com.example.physics_lab.databinding.FragmentSetMarkBinding
import com.example.physics_lab.databinding.FragmentSolvedWorksBinding
import com.example.physics_lab.service.SolutionService
import com.example.physics_lab.ui._base.BaseFragment
import com.example.physics_lab.ui.solved_works.SolvedWorksViewModel
import com.example.physics_lab.ui.solved_works.SolvedWorksViewModelFactory


class SetMark : BaseFragment<FragmentSetMarkBinding>() {
    lateinit var solutionService: SolutionService
    lateinit var viewModel: SetMarkViewModel

    override fun getLayoutRes(): Int {
        return R.layout.fragment_set_mark
    }

    override fun provideViewModel() {
        viewModel =
            SetMarkViewModelFactory(requireContext()).create(SetMarkViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        solutionService = SolutionService(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.viewModel = viewModel
        initText(view)
        observeFields()
        setListeners()
    }

    private fun observeFields() {
        viewModel.apiExceptionData.observe(viewLifecycleOwner, apiExceptionObserver)
        viewModel.activeSolution.observe(viewLifecycleOwner, {
            navController.popBackStack()
            context?.let { it1 -> hideKeyboard(it1) }
        })
    }

    private fun initText(view: View) {
        val nameText = view.findViewById<TextView>(R.id.valueNameText)
        val resultText = view.findViewById<TextView>(R.id.valueResultText)
        val dateText = view.findViewById<TextView>(R.id.dateFinishText)

        nameText.text = solutionService.name ?: ""
        resultText.text = solutionService.result ?: ""
        dateText.text = solutionService.dateOfDownload ?: ""
    }

    private fun setListeners() {
        viewBinding.setMarkButton.setOnClickListener {
            val mark = viewModel.mark.value ?: ""
            if (mark.isNotEmpty())
                viewModel.setMarkResponse(mark.toInt())
            else viewModel.apiExceptionData.value = "Поле пустое"
        }
        viewBinding.videoButton.setOnClickListener {
            val url = solutionService.videoPath
            if (url != null) {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url)
                )
                startActivity(browserIntent)
            }
        }
    }

}