package com.example.physics_lab.ui._items

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.example.physics_lab.R
import com.example.physics_lab.data.ClassRoomItem
import com.example.physics_lab.databinding.ListItemResClassBinding
import com.xwray.groupie.viewbinding.BindableItem
import java.util.*


class ClassListItem(val item: ClassRoomItem): BindableItem<ListItemResClassBinding>() {
    override fun bind(viewBinding: ListItemResClassBinding, position: Int) {
        viewBinding.className = item.title
    }


    override fun getLayout(): Int = R.layout.list_item_res_class

    override fun initializeViewBinding(view: View): ListItemResClassBinding {
        return ListItemResClassBinding.bind(view)
    }
}