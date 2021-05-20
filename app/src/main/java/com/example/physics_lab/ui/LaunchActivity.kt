package com.example.physics_lab.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.physics_lab.R
import com.example.physics_lab.databinding.ActivityLaunchBinding
import com.example.physics_lab.service.AuthService
import com.example.physics_lab.ui._base.BaseActivity

class LaunchActivity : BaseActivity<ActivityLaunchBinding>() {
    private lateinit var navController: NavController
    private val authService by lazy {
        AuthService(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        checkLocalData()
    }

    override fun getContentViewResId(): Int {
        return R.layout.activity_launch
    }

    private fun checkLocalData() {
        if (authService.token == null) {
            navController.navigate(R.id.welcomeFragment)
        } else navController.navigate(R.id.classListFragment)
    }

}