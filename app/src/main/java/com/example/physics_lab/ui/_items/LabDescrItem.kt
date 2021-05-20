package com.example.physics_lab.ui._items

import android.util.Log
import android.view.View
import com.example.physics_lab.R
import com.example.physics_lab.databinding.ListItemLabDescrBinding
import com.example.physics_lab.databinding.ListItemResLabsBinding
import com.xwray.groupie.viewbinding.BindableItem
import java.text.SimpleDateFormat

class LabDescrItem (val descrTopic: String?, val descrDescr: String?): BindableItem<ListItemLabDescrBinding>() {
    override fun bind(viewBinding: ListItemLabDescrBinding, position: Int) {
        viewBinding.descrTopic = descrTopic ?: "Пустой заголовок"
        viewBinding.descrDescrp = descrDescr ?: "Пустое описание"
    }

    override fun getLayout(): Int {
        return R.layout.list_item_lab_descr
    }

    override fun initializeViewBinding(view: View): ListItemLabDescrBinding {
        return ListItemLabDescrBinding.bind(view)
    }


}