package com.example.physics_lab.ui.class_description

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.example.physics_lab.R
import com.example.physics_lab.databinding.FragmentClassDescriptionBinding
import com.example.physics_lab.databinding.FragmentStudClassScreenBinding
import com.example.physics_lab.service.AuthService
import com.example.physics_lab.service.ClassService
import com.example.physics_lab.ui._base.BaseFragment
import com.example.physics_lab.ui.auth.signin.SignInViewModel
import com.example.physics_lab.ui.class_list.ClassListViewModel
import com.example.physics_lab.ui.class_list.ClassListViewModelFactory
import com.google.android.material.button.MaterialButton

class ClassDescription  : BaseFragment<FragmentClassDescriptionBinding>(){

    lateinit var viewModel: ClassDescriptionViewModel
    lateinit var classService: ClassService


    override fun getLayoutRes(): Int {
        return R.layout.fragment_class_description
    }

    override fun provideViewModel() {
        viewModel = ViewModelProviders.of(this).get(ClassDescriptionViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        classService = ClassService(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initText(view)
        setOnClick(view)
     }

    private fun initText(view: View) {
        val nameText = view.findViewById<TextView>(R.id.aboutNameClassText)
        val codeText = view.findViewById<TextView>(R.id.codeClassText)

        nameText.text = classService.title
        codeText.text = classService.code
    }

    private fun setOnClick(view: View) {
        val button = view.findViewById<MaterialButton>(R.id.aboutClassButton)
        button.setOnClickListener {
            navController.popBackStack()
        }
    }
}