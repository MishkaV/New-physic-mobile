package com.example.physics_lab.ui._base

import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    lateinit var viewBinding: T
    protected val scopeMain = CoroutineScope(Dispatchers.Main)
    protected var progressView: CardView? = null

    abstract fun getContentViewResId(): Int

    fun showLoading() {
        progressView?.visibility = View.VISIBLE
    }

    fun hideLoading() {
        progressView?.visibility = View.GONE
    }

    fun showToast(text: String) {
        val toast = Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun inflateContentView(layoutInflater: LayoutInflater) {
        viewBinding = DataBindingUtil.setContentView(this, getContentViewResId())
    }

    protected fun launchFragment(
        fragment: Fragment,
        @IdRes containerId: Int
    ) {
        supportFragmentManager.beginTransaction()
            .add(containerId, fragment, fragment.javaClass.simpleName)
            .addToBackStack(fragment.javaClass.simpleName)
            .commit()
    }
}