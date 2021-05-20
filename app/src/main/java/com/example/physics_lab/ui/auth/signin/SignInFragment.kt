package com.example.physics_lab.ui.auth.signin

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.auth0.android.jwt.Claim
import com.example.physics_lab.R
import com.example.physics_lab.databinding.FragmentLoginScreenBinding
import com.example.physics_lab.service.AuthService
import com.example.physics_lab.ui._base.BaseFragment
import com.google.gson.Gson

class SignInFragment : BaseFragment<FragmentLoginScreenBinding>(), Observer<String> {

    val roleKey = "http://schemas.microsoft.com/ws/2008/06/identity/claims/role"

    lateinit var authService: AuthService
    lateinit var viewModel: SignInViewModel

    override fun getLayoutRes(): Int {
        return R.layout.fragment_login_screen
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        authService = AuthService(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.viewModel = viewModel
        observeFields()
        setListener()
    }

    override fun onChanged(t: String?) {
        checkSignInAbility()
    }

    private fun checkSignInAbility() {
        val email = viewModel.email.value ?: ""
        val password = viewModel.password.value ?: ""
        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewModel.fieldsAreEmpty = false
        }
    }

    private fun observeFields() {
        viewModel.apiExceptionData.observe(viewLifecycleOwner, apiExceptionObserver)
        viewModel.email.observe(viewLifecycleOwner, this)
        viewModel.password.observe(viewLifecycleOwner, this)
        viewModel.tokenData.observe(viewLifecycleOwner, {
            authService.saveToken(it)

            val data = authService.extractJwt(it)
            val payload = data["payload"] as Map<*, *>
            val role = payload[roleKey] as Claim

            authService.saveRole(role.asString().toString())
            navController.navigate(R.id.action_signInFragment_to_classListFragment)

            context?.let { it1 -> hideKeyboard(it1) }
        })
    }

    override fun provideViewModel() {
        viewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)
    }

    private fun setListener() {
        viewBinding.loginButtonFrag.setOnClickListener {
            val email = viewModel.email.value ?: ""
            val password = viewModel.password.value ?: ""
            if (viewModel.fieldsAreEmpty) {
                viewModel.apiExceptionData.value = "Поля пустые"
            } else {
                viewModel.signIn(email, password)
            }
        }
    }
}