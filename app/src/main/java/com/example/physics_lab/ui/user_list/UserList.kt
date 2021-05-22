package com.example.physics_lab.ui.user_list

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.physics_lab.R
import com.example.physics_lab.databinding.FragmentUserListBinding
import com.example.physics_lab.ui._base.BaseFragment
import com.example.physics_lab.ui._items.UserItem
import com.getbase.floatingactionbutton.FloatingActionButton
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class UserList  : BaseFragment<FragmentUserListBinding>(){
    lateinit var viewModel: UserListViewModel
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_user_list
    }

    override fun provideViewModel() {
        viewModel =
            UserListViewModelFactory(requireContext()).create(UserListViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        observeFields()
        loadClassInfo()
        setOnClick(view)
    }

    private fun setUpRecycler() {
        viewBinding.recyclerViewUsers.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
    }

    private fun  loadClassInfo() = viewModel.loadTeacherClass()

    private fun observeFields() {
        viewModel.classData.observe(viewLifecycleOwner, { rawData ->
            adapter.clear()
            rawData.users.map {
                adapter.add(UserItem(it, onAddClicked = { user->
                    viewModel.deleteStudentFromClass(user.id.toString())
                }))
            }
            adapter.notifyDataSetChanged()
        })
        viewModel.apiExceptionData.observe(viewLifecycleOwner, apiExceptionObserver)
        viewModel.removeResponse.observe(viewLifecycleOwner, {
            loadClassInfo()
        })
    }

    private fun setOnClick(view: View) {
        val addButton = view.findViewById<FloatingActionButton>(R.id.addUserButton)
        addButton.setOnClickListener {
            navController.navigate(R.id.action_userListFragment_to_addUserTeacherFragment)
        }
    }
}

