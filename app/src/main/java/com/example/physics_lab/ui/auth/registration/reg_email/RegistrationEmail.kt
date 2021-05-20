package com.example.physics_lab.ui.auth.registration.reg_email

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.physics_lab.R
import com.example.physics_lab.databinding.FragmentRegistrationEmailBinding
import com.example.physics_lab.service.RegistrationService
import com.example.physics_lab.ui._base.BaseFragment
import com.example.physics_lab.ui.auth.registration.reg_full_name.RegistrationFullNameViewModel

class RegistrationEmail : BaseFragment<FragmentRegistrationEmailBinding>(), Observer<String> {
    lateinit var viewModel: RegistrationEmailViewModel
    lateinit var registrationService: RegistrationService

    override fun getLayoutRes(): Int {
        return R.layout.fragment_registration_email
    }

    override fun provideViewModel() {
        viewModel = ViewModelProviders.of(this).get(RegistrationEmailViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        registrationService = RegistrationService(context)
    }

    override fun onChanged(t: String?) {
        checkSignInAbility()
    }

    private fun checkSignInAbility() {
        val email = viewModel.email.value ?: ""
        val password = viewModel.password.value ?: ""
        if (email.isNotEmpty() && password.isNotEmpty() ) {
            viewModel.fieldsAreEmpty = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.viewModel = viewModel
        observeFields()
        setListener()
    }

    private fun observeFields() {
        viewModel.apiExceptionData.observe(viewLifecycleOwner, apiExceptionObserver)
        viewModel.email.observe(viewLifecycleOwner, this)
        viewModel.password.observe(viewLifecycleOwner, this)
    }

    private fun setListener() {
        viewBinding.registrationEmailButton.setOnClickListener{
            val email = viewModel.email.value ?: ""
            val password = viewModel.password.value ?: ""
            if (viewModel.fieldsAreEmpty) {
                viewModel.apiExceptionData.value = "Fields are empty"
            } else {
                registrationService.saveEmail(email)
                registrationService.savePassword(password)
                navController.navigate(R.id.action_registrationEmailFragment_to_registrationRoleFragment)
                context?.let { it1 -> hideKeyboard(it1) }
            }
        }
    }

}