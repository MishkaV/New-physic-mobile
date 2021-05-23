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
}