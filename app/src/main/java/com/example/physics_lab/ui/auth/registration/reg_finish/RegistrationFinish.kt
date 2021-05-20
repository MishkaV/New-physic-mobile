package com.example.physics_lab.ui.auth.registration.reg_finish

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.example.physics_lab.R
import com.example.physics_lab.databinding.FragmentRegistrationEmailBinding
import com.example.physics_lab.databinding.FragmentRegistrationFinishBinding
import com.example.physics_lab.service.RegistrationService
import com.example.physics_lab.ui._base.BaseFragment
import com.example.physics_lab.ui.auth.registration.reg_email.RegistrationEmailViewModel


class RegistrationFinish : BaseFragment<FragmentRegistrationFinishBinding>() {
    lateinit var viewModel: RegistrationFinishViewModel

    override fun getLayoutRes(): Int {
        return R.layout.fragment_registration_finish
    }

    override fun provideViewModel() {
        viewModel = ViewModelProviders.of(this).get(RegistrationFinishViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.viewModel = viewModel
        initTagView()
        observeFields()
        setListener()
    }

    private fun observeFields() {
        viewModel.apiExceptionData.observe(viewLifecycleOwner, apiExceptionObserver)
    }

    private fun setListener() {
        viewBinding.registrationFinishButton.setOnClickListener{
            navController.navigate(R.id.action_registrationFinishFragment_to_classListFragment)
        }
    }

    private fun initTagView() {
        val tags = mutableListOf<VectorDrawableTagItem>()
        val tagSphereView = view?.findViewById<com.magicgoop.tagsphere.TagSphereView>(R.id.tagView)
        for(id in drawableResList){
            getVectorDrawable(id)?.let {
                tags.add(VectorDrawableTagItem(it))
            }
        }
        tagSphereView?.addTagList(tags)
        tagSphereView?.setRadius(2.75f)
    }

    private fun getVectorDrawable(id: Int): Drawable? =
        ContextCompat.getDrawable(requireContext(), id)

    companion object {
        fun newInstance(): RegistrationFinish =
            RegistrationFinish()

        var drawableResList = listOf(
            R.drawable.ic_example_1,
            R.drawable.ic_example_2,
            R.drawable.ic_example_3,
            R.drawable.ic_example_4,
            R.drawable.ic_example_5,
            R.drawable.ic_example_6,
            R.drawable.ic_example_7,
            R.drawable.ic_example_8,
            R.drawable.ic_example_9,
            R.drawable.ic_example_10,
            R.drawable.ic_example_11,
            R.drawable.ic_example_12,
            R.drawable.ic_example_13,
            R.drawable.ic_example_14,
            R.drawable.ic_example_15,
            R.drawable.ic_example_16,
            R.drawable.ic_example_17,
            R.drawable.ic_example_18,
            R.drawable.ic_example_19,
            R.drawable.ic_example_20,
            R.drawable.ic_example_21,
            R.drawable.ic_example_22,
            R.drawable.ic_example_23,
            R.drawable.ic_example_24,
            R.drawable.ic_example_25
        )
    }

}