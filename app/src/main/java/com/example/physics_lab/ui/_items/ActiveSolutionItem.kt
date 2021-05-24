package com.example.physics_lab.ui._items

import android.util.Log
import android.view.View
import com.example.physics_lab.R
import com.example.physics_lab.data.ActiveSolutionData
import com.example.physics_lab.data.Lab
import com.example.physics_lab.databinding.ListItemActiveSolutionBinding
import com.example.physics_lab.databinding.ListItemResLabsBinding
import com.xwray.groupie.viewbinding.BindableItem
import java.text.SimpleDateFormat

class ActiveSolutionItem (val item: ActiveSolutionData.Solution, val name : String): BindableItem<ListItemActiveSolutionBinding>() {

    override fun bind(viewBinding: ListItemActiveSolutionBinding, position: Int) {
        viewBinding.userName = name
        viewBinding.profileImage.setImageResource(getIcon(position))
        if (item.dateOfDownload == null)
            viewBinding.dateDownload = ""
        else
            viewBinding.dateDownload = "Дата загрузки: " + getDate(item.dateOfDownload)
    }

    override fun getLayout(): Int {
        return R.layout.list_item_active_solution
    }

    override fun initializeViewBinding(view: View): ListItemActiveSolutionBinding {
        return ListItemActiveSolutionBinding.bind(view)
    }

    fun getDate(dateStr: String) : String{
        try {
            val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("dd MMMM yyyy")
            val formattedDate = formatter.format(parser.parse(dateStr))
            return formattedDate.toString()
        } catch (e: Exception){
            return ""
        }
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