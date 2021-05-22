package com.example.physics_lab.ui._items

import android.view.View
import androidx.core.view.isVisible
import com.example.physics_lab.R
import com.example.physics_lab.data.ClassUserData
import com.example.physics_lab.data.LabTask
import com.example.physics_lab.databinding.ListItemUserBinding
import com.xwray.groupie.viewbinding.BindableItem

class UserItem(val item: ClassUserData.User, val onAddClicked: (user: ClassUserData.User) -> Unit): BindableItem<ListItemUserBinding>() {

    override fun getLayout(): Int = R.layout.list_item_user

    override fun bind(viewBinding: ListItemUserBinding, position: Int) {
        viewBinding.userName  =  item.name ?: ""
        if (item.isTeacher == true) {
            viewBinding.deleteButton.isEnabled = false
            viewBinding.deleteButton.isVisible = false
        }
        else {
            viewBinding.deleteButton.setOnClickListener {
                onAddClicked(item)
            }
        }
    }

    override fun initializeViewBinding(view: View): ListItemUserBinding {
        return ListItemUserBinding.bind(view)
    }

}