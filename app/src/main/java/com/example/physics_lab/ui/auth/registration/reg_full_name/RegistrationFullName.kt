package com.example.physics_lab.ui.auth.registration.reg_full_name

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.auth0.android.jwt.Claim
import com.example.physics_lab.R
import com.example.physics_lab.databinding.FragmentLabListBinding
import com.example.physics_lab.databinding.FragmentRegistrationFullNameBinding
import com.example.physics_lab.service.AuthService
import com.example.physics_lab.service.ClassService
import com.example.physics_lab.service.RegistrationService
import com.example.physics_lab.ui._base.BaseFragment
import com.example.physics_lab.ui.auth.signin.SignInViewModel
import com.example.physics_lab.ui.lab_list.LabListViewModel
import com.example.physics_lab.ui.lab_list.LabListViewModelFactory


class RegistrationFullName : BaseFragment<FragmentRegistrationFullNameBinding>(), Observer<String> {

    lateinit var viewModel: RegistrationFullNameViewModel
    lateinit var registrationService: RegistrationService

    override fun getLayoutRes(): Int {
        return R.layout.fragment_registration_full_name
    }

    override fun provideViewModel() {
        viewModel = ViewModelProviders.of(this).get(RegistrationFullNameViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        registrationService = RegistrationService(context)
    }

    override fun onChanged(t: String?) {
        checkSignInAbility()
    }

    private fun checkSignInAbility() {
        val name = viewModel.name.value ?: ""
        val surname = viewModel.surname.value ?: ""
        val patronymic = viewModel.patronymic.value ?: ""
        if (name.isNotEmpty() && surname.isNotEmpty() && patronymic.isNotEmpty()) {
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
        viewModel.name.observe(viewLifecycleOwner, this)
        viewModel.surname.observe(viewLifecycleOwner, this)
        viewModel.patronymic.observe(viewLifecycleOwner, this)
    }

    private fun setListener() {
        viewBinding.registrationFullNameButton.setOnClickListener{
            val name = viewModel.name.value ?: ""
            val surname = viewModel.surname.value ?: ""
            val patronymic = viewModel.patronymic.value ?: ""
            Log.d("TAG_REG", name)
            if (viewModel.fieldsAreEmpty) {
                viewModel.apiExceptionData.value = "Fields are empty"
            } else {
                registrationService.saveFullName(name + " " + surname + " " + patronymic)
                navController.navigate(R.id.action_registrationFullNameFragment_to_registrationEmailFragment)
                context?.let { it1 -> hideKeyboard(it1) }
            }
        }
    }

}