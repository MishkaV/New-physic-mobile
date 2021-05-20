package com.example.physics_lab.ui._base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    lateinit var navController: NavController
    lateinit var viewBinding: T
    protected val scopeMain = CoroutineScope(Dispatchers.Main)
    protected val apiExceptionObserver = Observer<String> {
        showToast(it)
    }

    abstract fun getLayoutRes(): Int

    abstract fun provideViewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflateView(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        provideViewModel()
    }

    protected fun showToast(text: String) {
        (activity as? BaseActivity<*>)?.apply {
            this.showToast(text)
        }
    }

    private fun inflateView(layoutInflater: LayoutInflater) {
        viewBinding = DataBindingUtil.inflate(
            layoutInflater, getLayoutRes(), null, false
        )
        viewBinding.lifecycleOwner = this
    }

    protected fun showLoading() {
        (activity as? BaseActivity<*>)?.apply {
            this.showLoading()
        }
    }

    protected fun hideLoading() {
        (activity as? BaseActivity<*>)?.apply {
            this.hideLoading()
        }
    }
    protected fun hideKeyboard(context: Context) {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}