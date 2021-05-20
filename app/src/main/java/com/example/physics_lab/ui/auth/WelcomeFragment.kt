package com.example.physics_lab.ui.auth

import android.os.Bundle
import android.view.View
import com.example.physics_lab.R
import com.example.physics_lab.databinding.FragmentStartScreenBinding
import com.example.physics_lab.ui._base.BaseFragment

class WelcomeFragment : BaseFragment<FragmentStartScreenBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_start_screen
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    private fun setClickListeners() {
        viewBinding.buttonLogin.setOnClickListener {
            navController.navigate(R.id.action_welcomeFragment_to_signInFragment)
        }
        viewBinding.buttonRegistration.setOnClickListener {
            navController.navigate(R.id.action_welcomeFragment_to_registrationFullNameFragment)
        }
    }

    override fun provideViewModel() {

    }
}
