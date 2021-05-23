package com.example.physics_lab.ui._items

import android.view.View
import androidx.core.view.isVisible
import com.example.physics_lab.R
import com.example.physics_lab.data.ClassUserData
import com.example.physics_lab.data.LabTask
import com.example.physics_lab.databinding.ListItemUserBinding
import com.xwray.groupie.viewbinding.BindableItem
import kotlin.random.Random

class UserItem(val item: ClassUserData.User, val onAddClicked: (user: ClassUserData.User) -> Unit): BindableItem<ListItemUserBinding>() {

    override fun getLayout(): Int = R.layout.list_item_user

    override fun bind(viewBinding: ListItemUserBinding, position: Int) {
        viewBinding.userName  =  item.name ?: ""
        viewBinding.profileImage.setImageResource(getIcon(position))
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

    private fun getIcon(position: Int): Int {
        if (position % 10 == 0)
            return R.drawable.avatar1
        if (position % 10 == 1)
            return R.drawable.avatar2
        if (position % 10 == 2)
            return R.drawable.avatar3
        if (position % 10 == 3)
            return R.drawable.avatar4
        if (position % 10 == 4)
            return R.drawable.avatar5
        if (position % 10 == 5)
            return R.drawable.avatar6
        if (position % 10 == 6)
            return R.drawable.avatar7
        if (position % 10 == 7)
            return R.drawable.avatar8
        if (position % 10 == 8)
            return R.drawable.avatar8
        return R.drawable.avatar10
    }
}