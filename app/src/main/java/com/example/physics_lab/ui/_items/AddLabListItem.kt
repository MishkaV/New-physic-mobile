package com.example.physics_lab.ui._items

import android.view.View
import com.example.physics_lab.R
import com.example.physics_lab.data.LabTask
import com.example.physics_lab.databinding.ListItemResAddLabBinding
import com.xwray.groupie.viewbinding.BindableItem

class AddLabListItem(val labTask: LabTask, val onAddClicked: (labTask: LabTask) -> Unit) :
    BindableItem<ListItemResAddLabBinding>() {
    override fun bind(viewBinding: ListItemResAddLabBinding, position: Int) {
        viewBinding.addLabName = labTask.name
        viewBinding.addLabTheme = labTask.theme
        viewBinding.addLabResButton.setOnClickListener {

            onAddClicked(labTask)
        }
    }

    override fun getLayout(): Int {
        return R.layout.list_item_res_add_lab
    }

    override fun initializeViewBinding(view: View): ListItemResAddLabBinding {
        return ListItemResAddLabBinding.bind(view)
    }
}