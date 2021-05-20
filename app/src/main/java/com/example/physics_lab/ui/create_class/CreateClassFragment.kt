package com.example.physics_lab.ui.create_class

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.physics_lab.R
import com.example.physics_lab.data.ClassRoomItem
import com.example.physics_lab.databinding.TeacherAddClassBinding
import com.example.physics_lab.ui._base.BaseFragment

class CreateClassFragment : BaseFragment<TeacherAddClassBinding>(), Observer<ClassRoomItem> {
    lateinit var viewModel: CreateClassViewModel

    override fun getLayoutRes(): Int {
        return R.layout.teacher_add_class
    }

    override fun provideViewModel() {
        viewModel =
            CreateClassViewModelFactory(requireContext()).create(CreateClassViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
        setListeners()
        observeFields()
    }

    private fun bind() {
        viewBinding.viewModel = viewModel
    }

    private fun createClass() = viewModel.createClass()

    private fun setListeners() {
        viewBinding.createClassButton.setOnClickListener {
            val className = viewModel.className.value ?: ""
            if (className.isEmpty()) {
                showToast("Fields are empty")
            } else {
                createClass()
            }
        }
    }

    private fun observeFields() {
        viewModel.apiExceptionData.observe(viewLifecycleOwner, apiExceptionObserver)
        viewModel.newClassData.observe(viewLifecycleOwner, this)
    }

    override fun onChanged(t: ClassRoomItem?) {
        showToast("Class created")
        navController.navigate(R.id.classListFragment)
        hideKeyboard(requireContext())
    }
}