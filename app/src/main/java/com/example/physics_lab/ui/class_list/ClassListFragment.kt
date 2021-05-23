package com.example.physics_lab.ui.class_list

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.physics_lab.R
import com.example.physics_lab.data.ClassRoomItem
import com.example.physics_lab.databinding.FragmentStudClassScreenBinding
import com.example.physics_lab.service.AuthService
import com.example.physics_lab.service.ClassService
import com.example.physics_lab.service.StatisticService
import com.example.physics_lab.ui._base.BaseFragment
import com.example.physics_lab.ui._items.ClassListItem
import com.getbase.floatingactionbutton.FloatingActionButton
import com.getbase.floatingactionbutton.FloatingActionsMenu
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import timber.log.Timber

class ClassListFragment : BaseFragment<FragmentStudClassScreenBinding>() {
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }
    lateinit var authService: AuthService
    lateinit var classService: ClassService
    lateinit var statisticService: StatisticService
    lateinit var viewModel: ClassListViewModel
    override fun getLayoutRes(): Int {
        return R.layout.fragment_stud_class_screen
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        authService = AuthService(context)
        classService = ClassService(context)
        statisticService = StatisticService(context)
    }

    override fun onResume() {
        super.onResume()
        val floatMenu = view?.findViewById<FloatingActionsMenu>(R.id.menu_class)
        if (floatMenu != null) {
            floatMenu.collapse()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        observeFields(view)
        loadClasses()
        initButtons(view)
        setListeners()
    }

    override fun provideViewModel() {
        viewModel =
            ClassListViewModelFactory(requireContext()).create(ClassListViewModel::class.java)
    }

    private fun initButtons(view: View) {
        val floatMenu = view.findViewById<FloatingActionsMenu>(R.id.menu_class)
        if (authService.role == "teacher") {
            val floatAddClass = FloatingActionButton(context)
            floatAddClass.title = "Добавить класс"
            floatAddClass.setColorNormalResId(R.color.colorMainWhite)
            floatAddClass.setIcon(R.drawable.ic_presentation)
            floatAddClass.setOnClickListener {
                navController.navigate(R.id.action_classListFragment_to_addClassFragment)
            }
            floatMenu.addButton(floatAddClass)
        }
        else {
            val floatJoinClass = FloatingActionButton(context)
            floatJoinClass.title = "Присоединиться к классу"
            floatJoinClass.setColorNormalResId(R.color.colorMainWhite)
            floatJoinClass.setIcon(R.drawable.ic_join)
            floatJoinClass.setOnClickListener {
                navController.navigate(R.id.action_classListFragment_to_joinClassFragment)
            }
            floatMenu.addButton(floatJoinClass)
        }
    }

    private fun setUpRecycler() {
        viewBinding.recyclerViewStudClass.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
    }

    private fun loadClasses() {
        val role = authService.role
        Timber.i("role $role")
        Timber.i(authService.token)
        if (role == "student") {
            viewModel.loadStudentClasses()
        }
        if (role == "teacher") {
            viewModel.loadTeacherClasses()
        }
    }

    private fun observeFields(view: View) {
        viewModel.classData.observe(viewLifecycleOwner, { classRooms ->
            adapter.clear()
            adapter.addAll(classRooms.map {
                ClassListItem(it)
            })
            adapter.notifyDataSetChanged()
            val emptyLayout = view.findViewById<LinearLayout>(R.id.emptyLayout)
            if (adapter.itemCount == 0) {
                initText(view)
                emptyLayout.visibility = View.VISIBLE
            }
            else {
                emptyLayout.visibility = View.INVISIBLE
            }

        })
        viewModel.apiExceptionData.observe(viewLifecycleOwner, apiExceptionObserver)
    }

    private fun initText(view: View) {
        val textViewMain = view.findViewById<TextView>(R.id.emptyMainText)
        val textViewDescr = view.findViewById<TextView>(R.id.emptyDesrpText)
        if (authService.role == "student") {
            textViewMain.text = "Пока нет классов("
            textViewDescr.text = "Но ты всегда можешь присоединиться к существующему классу!"
        }
        else {
            textViewMain.text = "Пока нет ни одного класса!"
            textViewDescr.text = "Попробуйте создать новый класс, нажав на кнопку добавления"
        }
    }
    private fun setListeners() {
        val role = authService.role
        adapter.setOnItemClickListener { item, view ->
            val classRoomItem = item as ClassListItem
            classService.saveClassId(classRoomItem.item.id.toString())
            classService.saveTitle(classRoomItem.item.title)
            classService.saveCode(classRoomItem.item.code)
            navController.navigate(R.id.action_classListFragment_to_labListFragment)
        }

        viewBinding.logout.setOnClickListener {
            authService.logout()
            navController.navigate(R.id.action_classListFragment_to_welcomeFragment)
        }

        viewBinding.statAboutClassesButton.setOnClickListener {
            statisticService.savePrevLayout("classes")
            navController.navigate(R.id.action_classListFragment_to_statisticScreenFragment)
        }
    }
}
