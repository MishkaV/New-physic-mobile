package com.example.physics_lab.ui.statistic_screen

import android.content.Context
import android.os.Bundle
import android.util.ArrayMap
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.physics_lab.R
import com.example.physics_lab.data.ClassRoomItem
import com.example.physics_lab.data.ClassUserData
import com.example.physics_lab.data.PieChartData
import com.example.physics_lab.databinding.FragmentStatisticScreenBinding
import com.example.physics_lab.service.AuthService
import com.example.physics_lab.service.ClassService
import com.example.physics_lab.service.StatisticService
import com.example.physics_lab.ui._base.BaseFragment
import com.example.physics_lab.ui._items.StatisticPieChartItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class StatisticScreen : BaseFragment<FragmentStatisticScreenBinding>() {
    lateinit var authService: AuthService
    lateinit var classService: ClassService
    lateinit var statisticService: StatisticService
    lateinit var listData: ArrayMap<String, ArrayList<PieChartData>>

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }
    lateinit var viewModel: StatisticScreenViewModel

    override fun getLayoutRes(): Int {
        return R.layout.fragment_statistic_screen
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        authService = AuthService(context)
        classService = ClassService(context)
        statisticService = StatisticService(context)
    }

    override fun provideViewModel() {
        viewModel =
            StatisticScreenViewModelFactory(requireContext()).create(StatisticScreenViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        observeFields(view)
        initData()
    }

    private fun setUpRecycler() {
        viewBinding.recyclerViewStatistic.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
    }

    private fun initData() {
        listData = ArrayMap<String, ArrayList<PieChartData>>()
        when (statisticService.prevLayout) {
            "classes" -> {
                adapter.clear()
                listData = putData(listData, generateByCountClasses(statisticService.countClasses), "Классы")
                listData.map {
                    adapter.add(StatisticPieChartItem(it.key, it.value))
                }
                adapter.notifyDataSetChanged()
            }
            "labs" -> {
                adapter.clear()
                listData = putData(listData, generateByCountLabs(statisticService.countLabs), "Задания")
                listData = putData(listData, generateByUsers(statisticService.countLabs), "Пользователи")
                listData.map {
                    adapter.add(StatisticPieChartItem(it.key, it.value))
                }
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun observeFields(view: View) {
        viewModel.apiExceptionData.observe(viewLifecycleOwner, apiExceptionObserver)
    }

    private fun generateByCountLabs(countLabs: Float?): HashMap<String, Float> {
        var listData = HashMap<String, Float>()

        if (countLabs != null) {
            listData["Количество заданий"] = countLabs
        } else {
            listData["Количество заданий"] = 0F
        }

        return listData
    }

    private fun generateByUsers(countUsers: Float?): HashMap<String, Float> {
        var listData = HashMap<String, Float>()

        if (countUsers != null) {
            listData["Количество пользователей"] = countUsers
        } else {
            listData["Количество пользователей"] = 0F
        }
        return listData
    }

    private fun generateByCountClasses(countClasses: Float?): HashMap<String, Float> {
        var listData = HashMap<String, Float>()
        if (countClasses != null)
            listData["Количество классов"] = countClasses
        else
            listData["Количество классов"] = 0F
        return listData
    }

    private fun putData(
        listData: ArrayMap<String, ArrayList<PieChartData>>,
        listUploadData: HashMap<String, Float>,
        theme: String
    )
            : ArrayMap<String, ArrayList<PieChartData>> {
        var arrayListPieData = ArrayList<PieChartData>()

        for (data in listUploadData) {
            var pieChartData = PieChartData()
            pieChartData.value = data.value
            pieChartData.description = data.key
            arrayListPieData.add(pieChartData)
        }
        listData.put(theme, arrayListPieData)

        return listData
    }
}