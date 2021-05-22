package com.example.physics_lab.ui.user_list.add_user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.physics_lab.R
import com.example.physics_lab.databinding.FragmentAddUserTeacherBinding
import com.example.physics_lab.databinding.FragmentJoinClassScreenBinding
import com.example.physics_lab.ui._base.BaseFragment
import com.example.physics_lab.ui.join_to_class.JoinClassViewModel
import com.example.physics_lab.ui.join_to_class.JoinClassViewModelFactory

class AddUserTeacher  : BaseFragment<FragmentAddUserTeacherBinding>() {
    lateinit var viewModel: AddUserTeacherViewModel

    override fun getLayoutRes(): Int {
        return R.layout.fragment_add_user_teacher
    }

    override fun provideViewModel() {
        viewModel =
            AddUserTeacherViewModelFactory(requireContext()).create(AddUserTeacherViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.viewModel = viewModel
        observeFields()
        setListeners()
    }

    private fun observeFields() {
        viewModel.apiExceptionData.observe(viewLifecycleOwner, apiExceptionObserver)
        viewModel.postUserResponse.observe(viewLifecycleOwner, {
            apiExceptionObserver.onChanged("Присоединени к классу прошло успешно")
            navController.popBackStack()
            context?.let { it1 -> hideKeyboard(it1) }
        })
    }

    private fun setListeners() {
        viewBinding.addStudentButton.setOnClickListener {
            val email = viewModel.email.value ?: ""
            if (email.isNotEmpty())
                viewModel.joinStudentToClass(email)
            else viewModel.apiExceptionData.value = "Поле пустое"
        }
    }
}