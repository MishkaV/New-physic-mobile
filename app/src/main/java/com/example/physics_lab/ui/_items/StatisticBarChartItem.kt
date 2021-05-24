package com.example.physics_lab.ui._items

import android.graphics.Color
import android.view.View
import com.example.physics_lab.R
import com.example.physics_lab.data.BarChartData
import com.example.physics_lab.data.PieChartData
import com.example.physics_lab.databinding.ListItemBarchartBinding
import com.example.physics_lab.databinding.ListItemPiechartBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.xwray.groupie.viewbinding.BindableItem

class StatisticBarChartItem(var topic: String, var value: ArrayList<BarChartData>, var listDataByGroup: ArrayList<ArrayList<String>>): BindableItem<ListItemBarchartBinding>() {

    override fun bind(viewBinding: ListItemBarchartBinding, position: Int) {
        viewBinding.barChartItem.data = createBarChart(position)
        viewBinding.barChartItem.setFitBars(true)
        viewBinding.barChartItem.animateY(1000)
        viewBinding.barChartItem.description.text = ""
        viewBinding.barChartItem.xAxis.setLabelCount(listDataByGroup[0].size)
        viewBinding.barChartItem.xAxis.valueFormatter = IndexAxisValueFormatter(listDataByGroup[0])
    }

    override fun initializeViewBinding(view: View): ListItemBarchartBinding {
        return ListItemBarchartBinding.bind(view)
    }

    override fun getLayout(): Int {
        return R.layout.list_item_barchart
    }

    private fun createBarChart(position: Int): BarData {
        val data = ArrayList<BarEntry>()
        for (item in value) {
            data.add(BarEntry(item.x, item.y))
        }

        val barDataSet = BarDataSet(data, topic)
        if (position % 2  == 0)
            barDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        else
            barDataSet.colors = ColorTemplate.JOYFUL_COLORS.toList()
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 16f

        return BarData(barDataSet)
    }
}