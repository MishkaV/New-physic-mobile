package com.example.physics_lab.ui.lab_list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.physics_lab.R
import com.example.physics_lab.data.ClassRoomItem
import com.example.physics_lab.databinding.FragmentLabListBinding
import com.example.physics_lab.service.AuthService
import com.example.physics_lab.service.ClassService
import com.example.physics_lab.service.LabService
import com.example.physics_lab.service.StatisticService
import com.example.physics_lab.ui._base.BaseFragment
import com.example.physics_lab.ui._items.LabListItem
import com.getbase.floatingactionbutton.FloatingActionButton
import com.getbase.floatingactionbutton.FloatingActionsMenu
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import timber.log.Timber

class LabListFragment : BaseFragment<FragmentLabListBinding>() {
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }
    lateinit var authService: AuthService
    lateinit var classService: ClassService
    lateinit var labService: LabService
    lateinit var statisticService: StatisticService
    lateinit var viewModel: LabListViewModel
    override fun getLayoutRes(): Int {
        return R.layout.fragment_lab_list
    }

    override fun onResume() {
        super.onResume()
        val floatMenu = view?.findViewById<FloatingActionsMenu>(R.id.menu_buttons_lab)
        if (floatMenu != null) {
            floatMenu.collapse()
        }
    }

    override fun provideViewModel() {
        viewModel =
            LabListViewModelFactory(requireContext()).create(LabListViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        authService = AuthService(context)
        classService = ClassService(context)
        labService = LabService(context)
        statisticService = StatisticService(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        observeFields(view)
        loadClass()
        initButtons(view)
        setOnClick()
    }

    private fun setUpRecycler() {
        viewBinding.recyclerViewLab.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
    }

    private fun initButtons(view: View) {
        val floatMenu = view.findViewById<FloatingActionsMenu>(R.id.menu_buttons_lab)
        if (authService.role == "teacher") {
            initTeacherButtons(floatMenu)
        }
        else {
            initStudentButtons(floatMenu)
        }
        initGeneralButtons(floatMenu)
    }

    private fun initTeacherButtons(floatMenu: FloatingActionsMenu) {
        val floatAddLab = FloatingActionButton(context)
        floatAddLab.title = "Добавить задание"
        floatAddLab.setColorNormalResId(R.color.colorMainWhite)
        floatAddLab.setIcon(R.drawable.ic_add_task)
        floatAddLab.setOnClickListener {
            navController.navigate(R.id.action_labListFragment_to_addLabFragment)
        }
        floatMenu.addButton(floatAddLab)

        val floatUsers = FloatingActionButton(context)
        floatUsers.title = "Пользователи класса"
        floatUsers.setColorNormalResId(R.color.colorMainWhite)
        floatUsers.setIcon(R.drawable.ic_user)
        floatUsers.setOnClickListener {
            navController.navigate(R.id.action_labListFragment_to_userListFragment)
        }
        floatMenu.addButton(floatUsers)

        val floatDeleteClass = FloatingActionButton(context)
        floatDeleteClass.title = "Удалить класс"
        floatDeleteClass.setIcon(R.drawable.ic_delete_blue)
        floatDeleteClass.setColorNormalResId(R.color.colorMainWhite)
        floatDeleteClass.setOnClickListener {
            viewModel.removeClass()
        }
        floatMenu.addButton(floatDeleteClass)
    }

    private fun initStudentButtons(floatMenu: FloatingActionsMenu) {
        val floatActiveLab = FloatingActionButton(context)
        floatActiveLab.title = "Активные решения"
        floatActiveLab.setColorNormalResId(R.color.colorMainWhite)
        floatActiveLab.setIcon(R.drawable.ic_active)
        floatActiveLab.setOnClickListener {
            navController.navigate(R.id.action_labListFragment_to_activeLabsFragment)
        }
        floatMenu.addButton(floatActiveLab)

        val floatFinishLab = FloatingActionButton(context)
        floatFinishLab.title = "Завершенные работы"
        floatFinishLab.setColorNormalResId(R.color.colorMainWhite)
        floatFinishLab.setIcon(R.drawable.ic_finish)
        floatFinishLab.setOnClickListener {
            navController.navigate(R.id.action_labListFragment_to_finishLabFragment)
        }
        floatMenu.addButton(floatFinishLab)
    }

    private fun initGeneralButtons(floatMenu: FloatingActionsMenu) {
        val floatInfoClass = FloatingActionButton(context)
        floatInfoClass.title = "Информация о классе"
        floatInfoClass.setColorNormalResId(R.color.colorMainWhite)
        floatInfoClass.setIcon(R.drawable.ic_information)
        floatInfoClass.setOnClickListener {
            navController.navigate(R.id.action_labListFragment_to_classDescriptionFragment)
        }
        floatMenu.addButton(floatInfoClass)
    }


    private fun loadClass() {
        val role = authService.role
        Timber.i("role $role")
        Timber.i(authService.token)
        if (role == "student") {
            viewModel.loadStudentClass()
        }
        if (role == "teacher") {
            viewModel.loadTeacherClass()
        }
    }

    private fun observeFields(view: View) {
        viewModel.classData.observe(viewLifecycleOwner, { classRooms ->
            adapter.clear()
            classRooms.labs?.let {
                adapter.addAll(it.map { lab ->
                    LabListItem(lab)
                })
            }
            if (classRooms.labs != null)
                statisticService.saveCountLabs(classRooms.labs.size.toFloat())
            if (classRooms.userClasses != null)
                statisticService.saveCountUsersInClass(classRooms.userClasses.size.toFloat())

            adapter.notifyDataSetChanged()
            val emptyLabLayout = view.findViewById<LinearLayout>(R.id.emptyLabLayout)
            if (adapter.itemCount == 0 || classRooms == null) {
                initText(view)
                adapter.clear()
                emptyLabLayout.visibility = View.VISIBLE
            }
            else {
                emptyLabLayout.visibility = View.INVISIBLE
            }
        })
        viewModel.removeResponse.observe(viewLifecycleOwner, removeObserver)
        viewModel.apiExceptionData.observe(viewLifecycleOwner, apiExceptionObserver)

        viewModel.lostConnect.observe(viewLifecycleOwner, {
            adapter.clear()
            val emptyLayout = view.findViewById<LinearLayout>(R.id.emptyLabLayout)
            if (it == true) {
                emptyLayout.visibility = View.VISIBLE
            }
            else {
                emptyLayout.visibility = View.INVISIBLE
            }
        })
    }

    private fun initText(view: View) {
        val textViewMain = view.findViewById<TextView>(R.id.emptyLabMainText)
        val textViewDescr = view.findViewById<TextView>(R.id.emptyLabDesrpText)
        if (authService.role == "student") {
            textViewMain.text = "Учитель пока не создавал новых заданий"
            textViewDescr.text = "Но в скором времени оно обязательно появиться!"
        }
        else {
            textViewMain.text = "Пока нет ни одной лабораторной работы!"
            textViewDescr.text = "Стоит попробовать добавить новое задание"
        }
    }

    private val removeObserver = Observer<Unit> {
        showToast("Успешное удаление класса")
        hideKeyboard(requireContext())
        navController.navigate(R.id.classListFragment)
    }

    private fun setOnClick() {
        if (authService.role == "student") {
            adapter.setOnItemClickListener { item, view ->
                val labItem = item as LabListItem
                labService.saveLabId(labItem.item.id.toString())
                navController.navigate(R.id.action_labListFragment_to_labDescriptionFragment)
            }
        }
        else {
            adapter.setOnItemClickListener { item, view ->
                val labItem = item as LabListItem
                labService.saveLabId(labItem.item.id.toString())
                navController.navigate(R.id.action_labListFragment_to_solvedWorksFragment)
            }
        }
        viewBinding.statAboutClassButton.setOnClickListener {
            statisticService.savePrevLayout("labs")
            navController.navigate(R.id.action_labListFragment_to_statisticScreenFragment)
        }
    }
}