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
        loadData()
//        initButtons(view)
//        setOnClick()
    }

    private fun setUpRecycler() {
        viewBinding.recyclerViewStatistic.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
    }

    private fun loadData() {
        val role = authService.role
        if (statisticService.prevLayout != null) {
            if (role == "student") {
                when (statisticService.prevLayout) {
                    "classes" -> {
                        viewModel.loadStudentClasses()
                    }
                    "labs" -> {
                        viewModel.loadStudentClass()
                    }
                }
            }
            if (role == "teacher") {
                viewModel.loadTeacherClasses()
                when (statisticService.prevLayout) {
                    "classes" -> {
                        viewModel.loadTeacherClasses()
                    }
                    "labs" -> {
                        viewModel.loadTeacherClass()
                    }
                }
            }
        }
    }

    private fun observeFields(view: View) {
        viewModel.classData.observe(viewLifecycleOwner, { classRooms ->
            listData = ArrayMap<String, ArrayList<PieChartData>>()
            adapter.clear()
            initClassesData(classRooms)
            listData.map {
                adapter.add(StatisticPieChartItem(it.key, it.value))
            }
            adapter.notifyDataSetChanged()
        })
        viewModel.apiExceptionData.observe(viewLifecycleOwner, apiExceptionObserver)
        viewModel.singleClassData.observe(viewLifecycleOwner, { classRoom ->
            listData = ArrayMap<String, ArrayList<PieChartData>>()
            adapter.clear()
            initClassData(classRoom)
            listData.map {
                adapter.add(StatisticPieChartItem(it.key, it.value))
            }
            adapter.notifyDataSetChanged()
        })
    }

    private fun initClassData(classRoom: ClassUserData) {
        var data = generateByCountLabs(classRoom)
        if (data != null)
            listData = putData(listData, data, "Задания")
        data = generateByUsers(classRoom)
        if (data != null)
            listData = putData(listData, data, "Пользователи")
    }

    private fun initClassesData(classRooms: List<ClassRoomItem>) {
        listData = putData(listData, generateByCountClasses(classRooms), "Классы")
    }

    private fun generateByCountLabs(item: ClassUserData): HashMap<String, Float>? {
        var listData = HashMap<String, Float>()

        if (item.labs != null) {
            listData["Количество заданий"] = item.labs.size.toFloat()
        } else {
            return null
        }

        return listData
    }

    private fun generateByUsers(item: ClassUserData): HashMap<String, Float>? {
        var listData = HashMap<String, Float>()

        if (item.users != null) {
            listData["Количество пользователей"] = item.users.size.toFloat()
        } else {
            return null
        }
        return listData
    }

    private fun generateByCountClasses(item: List<ClassRoomItem>): HashMap<String, Float> {
        var listData = HashMap<String, Float>()
        if (item != null) {
            listData["Количество классов"] = item.size.toFloat()
        } else {
            listData["Количество классов"] = 0F
        }
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