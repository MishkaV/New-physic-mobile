package com.example.physics_lab.ui.auth.registration.reg_role

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.auth0.android.jwt.Claim
import com.example.physics_lab.R
import com.example.physics_lab.databinding.FragmentRegistrationEmailBinding
import com.example.physics_lab.databinding.FragmentRegistrationRoleBinding
import com.example.physics_lab.service.AuthService
import com.example.physics_lab.service.RegistrationService
import com.example.physics_lab.ui._base.BaseFragment
import com.example.physics_lab.ui.auth.registration.reg_email.RegistrationEmailViewModel
import com.example.physics_lab.ui.lab_list.LabListViewModel
import com.example.physics_lab.ui.lab_list.LabListViewModelFactory

class RegistrationRole : BaseFragment<FragmentRegistrationRoleBinding>() {
    lateinit var viewModel: RegistrationRoleViewModel
    lateinit var registrationService: RegistrationService
    lateinit var authService: AuthService
    val roleKey = "http://schemas.microsoft.com/ws/2008/06/identity/claims/role"

    override fun getLayoutRes(): Int {
        return R.layout.fragment_registration_role
    }

    override fun provideViewModel() {
        viewModel =
            RegistrationRoleViewModelFactory(requireContext()).create(RegistrationRoleViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        registrationService = RegistrationService(context)
        authService = AuthService(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.viewModel = viewModel
        observeFields()
        setListener(view)
    }

    private fun observeFields() {
        viewModel.apiExceptionData.observe(viewLifecycleOwner, apiExceptionObserver)
        viewModel.tokenData.observe(viewLifecycleOwner, {
            authService.saveToken(it)

            val data = authService.extractJwt(it)
            val payload = data["payload"] as Map<*, *>
            val role = payload[roleKey] as Claim

            authService.saveRole(role.asString().toString())
            navController.navigate(R.id.action_registrationRoleFragment_to_registrationFinishFragment)
        })
    }

    private fun setListener(view: View) {
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
        viewBinding.registrationRoleButton.setOnClickListener{
            val selected = view.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            if (selected.text == "Учитель") {
                registrationService.saveRole("Учитель")
                viewModel.registrationTeacher()
            } else {
                registrationService.saveRole("Учитель")
                viewModel.registrationStudnet()
            }
        }
    }

}