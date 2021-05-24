package com.example.physics_lab.ui._items

import android.util.Log
import android.view.View
import com.example.physics_lab.R
import com.example.physics_lab.data.ClassUserData
import com.example.physics_lab.databinding.ListItemResLabsBinding
import com.xwray.groupie.viewbinding.BindableItem
import java.text.SimpleDateFormat

class LabListItem(val item: ClassUserData.Lab, var layout: String): BindableItem<ListItemResLabsBinding>() {

    override fun bind(viewBinding: ListItemResLabsBinding, position: Int) {
        viewBinding.labName = item.title ?: "Лабораторная работа"
        if (item.deadline == null)
            viewBinding.labDeadline =  ""
        else
            viewBinding.labDeadline = "Дедлайн: " + getDate(item.deadline)
        initImage(layout, viewBinding)
    }

    override fun getLayout(): Int = R.layout.list_item_res_labs

    override fun initializeViewBinding(view: View): ListItemResLabsBinding {
        return ListItemResLabsBinding.bind(view)
    }

    private fun initImage(layout: String, viewBinding: ListItemResLabsBinding) {
        when(layout) {
            "lab_list" -> viewBinding.labImage.setImageResource(R.drawable.background_start)
            "active_labs" -> viewBinding.labImage.setImageResource(R.drawable.background2)
            "finish_labs" -> viewBinding.labImage.setImageResource(R.drawable.background3)
            else -> viewBinding.labImage.setImageResource(R.drawable.background4)
        }
    }
    fun getDate(dateStr: String) : String{
        try {
            Log.d("DATE", dateStr)
            val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("dd MMMM yyyy")
            val formattedDate = formatter.format(parser.parse(dateStr))
            Log.d("DATE", formattedDate)
            return formattedDate.toString()
        } catch (e: Exception){
            Log.e("mDate",e.toString()) // this never gets called either
            return ""
        }
    }

}