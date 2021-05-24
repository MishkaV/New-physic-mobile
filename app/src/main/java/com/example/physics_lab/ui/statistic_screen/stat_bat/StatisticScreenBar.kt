package com.example.physics_lab.ui.statistic_screen.stat_bat

import android.content.Context
import android.os.Bundle
import android.util.ArrayMap
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.auth0.android.jwt.Claim
import com.example.physics_lab.R
import com.example.physics_lab.data.BarChartData
import com.example.physics_lab.data.PieChartData
import com.example.physics_lab.databinding.FragmentLoginScreenBinding
import com.example.physics_lab.databinding.FragmentStatisticScreenBarBinding
import com.example.physics_lab.service.AuthService
import com.example.physics_lab.service.ClassService
import com.example.physics_lab.service.StatisticService
import com.example.physics_lab.ui._base.BaseFragment
import com.example.physics_lab.ui._items.StatisticBarChartItem
import com.example.physics_lab.ui._items.StatisticPieChartItem
import com.example.physics_lab.ui.auth.signin.SignInViewModel
import com.getbase.floatingactionbutton.FloatingActionButton
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class StatisticScreenBar : BaseFragment<FragmentStatisticScreenBarBinding>() {
    lateinit var statisticService: StatisticService
    lateinit var viewModel: StatisticScreenBarViewModel
    lateinit var listData: ArrayMap<String, ArrayList<BarChartData>>
    private var listDataByGroup = ArrayList<ArrayList<String>>()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        statisticService = StatisticService(context)
    }

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }
    override fun getLayoutRes(): Int {
        return R.layout.fragment_statistic_screen_bar
    }

    override fun provideViewModel() {
        viewModel = ViewModelProviders.of(this).get(StatisticScreenBarViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.viewModel = viewModel
        setUpRecycler()
        observeFields()
        initData()
        setOnClick(view)
    }

    private fun setUpRecycler() {
        viewBinding.recyclerViewStatisticBar.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
    }

    private fun observeFields() {
        viewModel.apiExceptionData.observe(viewLifecycleOwner, apiExceptionObserver)
    }

    private fun initData() {
        listData = ArrayMap<String, ArrayList<BarChartData>>()
        when (statisticService.prevLayout) {
            "classes" -> {
                adapter.clear()
                listData = putData(listData, generateByCountClasses(statisticService.countClasses), "Классы")
                listData.map {
                    adapter.add(StatisticBarChartItem(it.key, it.value, listDataByGroup))
                }
                adapter.notifyDataSetChanged()
            }
            "labs" -> {
                adapter.clear()
                listData = putData(listData, generateByCountLabs(statisticService.countLabs), "Задания")
                listData = putData(listData, generateByUsers(statisticService.countUsersInClass), "Пользователи")
                listData.map {
                    adapter.add(StatisticBarChartItem(it.key, it.value, listDataByGroup))
                }
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun generateByCountLabs(countLabs: Float?): HashMap<Float, Float> {
        var listData = HashMap<Float, Float>()
        var listDataThemes = ArrayList<String>()

        if (countLabs != null) {
            listData.put(1F ,countLabs)
        } else {
            listData.put(1F ,0F)
        }
        listDataThemes.add("Количество заданий")
        listDataByGroup.add(listDataThemes)
        return listData
    }

    private fun generateByUsers(countUsers: Float?): HashMap<Float, Float>  {
        var listData = HashMap<Float, Float>()
        var listDataThemes = ArrayList<String>()

        if (countUsers != null) {
            listData.put(1F ,countUsers)
        } else {
            listData.put(1F ,0F)
        }
        listDataThemes.add("Количество пользователей")
        listDataByGroup.add(listDataThemes)
        return listData
    }

    private fun generateByCountClasses(countClasses: Float?): HashMap<Float, Float> {
        var listData = HashMap<Float, Float>()
        var listDataThemes = ArrayList<String>()

        if (countClasses != null) {
            listData.put(1F ,countClasses)
        } else {
            listData.put(1F ,0F)
        }
        listDataThemes.add("Количество классов")
        listDataByGroup.add(listDataThemes)
        return listData
    }

    private fun putData(
        listData: ArrayMap<String, ArrayList<BarChartData>>,
        listUploadData: HashMap<Float, Float>,
        theme: String
    )
            : ArrayMap<String, ArrayList<BarChartData>> {
        var arrayListPieData = ArrayList<BarChartData>()

        for (data in listUploadData) {
            var pieChartData = BarChartData()
            pieChartData.x = data.key
            pieChartData.y = data.value
            arrayListPieData.add(pieChartData)
        }
        listData.put(theme, arrayListPieData)
        return listData
    }

    private fun setOnClick(view: View) {
        val button = view.findViewById<FloatingActionButton>(R.id.toPieChart)
        button.setOnClickListener {
            navController.popBackStack()
        }
    }
}