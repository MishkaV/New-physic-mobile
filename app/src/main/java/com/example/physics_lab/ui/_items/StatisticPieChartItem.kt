package com.example.physics_lab.ui._items

import android.graphics.Color
import android.util.ArrayMap
import android.view.View
import com.example.physics_lab.R
import com.example.physics_lab.data.ClassRoomItem
import com.example.physics_lab.data.PieChartData
import com.example.physics_lab.databinding.ListItemBarchartBinding
import com.example.physics_lab.databinding.ListItemPiechartBinding
import com.example.physics_lab.databinding.ListItemResClassBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.xwray.groupie.viewbinding.BindableItem

class StatisticPieChartItem(var topic: String, var value: ArrayList<PieChartData>): BindableItem<ListItemPiechartBinding>() {

    override fun bind(viewBinding: ListItemPiechartBinding, position: Int) {
        viewBinding.pieChartItem.data = createPieChart(position)
        viewBinding.pieChartItem.centerText = topic
        viewBinding.pieChartItem.animateY(1000, Easing.EaseInOutExpo)
        viewBinding.pieChartItem.description.isEnabled = false
    }

    override fun initializeViewBinding(view: View): ListItemPiechartBinding {
        return ListItemPiechartBinding.bind(view)
    }

    override fun getLayout(): Int {
        return R.layout.list_item_piechart
    }


    private fun createPieChart(position: Int): PieData {
        val data = ArrayList<PieEntry>()
        for (item in value) {
            data.add(PieEntry(item.value, item.description))
        }
        val pieDataSet = PieDataSet(data, "")
        if (position % 2  == 0)
            pieDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        else
            pieDataSet.colors = ColorTemplate.JOYFUL_COLORS.toList()
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.valueTextSize = 16f

        return PieData(pieDataSet)
    }
}