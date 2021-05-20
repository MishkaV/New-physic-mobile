package com.example.physics_lab.ui.join_to_class

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.physics_lab.R
import com.example.physics_lab.databinding.FragmentJoinClassScreenBinding
import com.example.physics_lab.ui._base.BaseFragment

class JoinClassFragment : BaseFragment<FragmentJoinClassScreenBinding>() {
    lateinit var viewModel: JoinClassViewModel

    override fun getLayoutRes(): Int = R.layout.fragment_join_class_screen

    override fun provideViewModel() {
        viewModel =
            JoinClassViewModelFactory(requireContext()).create(JoinClassViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.viewModel = viewModel
        observeFields()
        setListeners()
    }

    private fun observeFields() {
        viewModel.apiExceptionData.observe(viewLifecycleOwner, apiExceptionObserver)
        viewModel.classData.observe(viewLifecycleOwner, {
            apiExceptionObserver.onChanged("Присоединени к классу прошло успешно")
            navController.popBackStack()
            context?.let { it1 -> hideKeyboard(it1) }
        })
    }

    private fun setListeners() {
        viewBinding.joinClassButton.setOnClickListener {
            val code = viewModel.code.value ?: ""
            if (code.isNotEmpty())
                viewModel.joinToClass(code)
            else viewModel.apiExceptionData.value = "Поле пустое"
        }
    }
}