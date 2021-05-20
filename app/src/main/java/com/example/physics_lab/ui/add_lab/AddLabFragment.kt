package com.example.physics_lab.ui.add_lab

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.example.physics_lab.R
import com.example.physics_lab.data.Lab
import com.example.physics_lab.data.LabTask
import com.example.physics_lab.databinding.TeacherAddLabBinding
import com.example.physics_lab.service.ClassService
import com.example.physics_lab.ui._base.BaseFragment
import com.example.physics_lab.ui._items.AddLabListItem
import com.google.android.material.button.MaterialButton
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*


class AddLabFragment : BaseFragment<TeacherAddLabBinding>() {
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val classService by lazy {
        ClassService(requireContext())
    }

    lateinit var viewModel: AddLabViewModel
    override fun getLayoutRes(): Int {
        return R.layout.teacher_add_lab
    }

    override fun provideViewModel() {
        viewModel = AddLabVieModelFactory(requireContext()).create(AddLabViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFields()
        setUpRecycler()
        loadLabs()
        setListeners(view)
    }

    private fun loadLabs() = viewModel.loadLabTasks()

    private fun observeFields() {
        viewModel.apiExceptionData.observe(viewLifecycleOwner, apiExceptionObserver)
        viewModel.labTaskData.observe(viewLifecycleOwner, labTasksObserver)
        viewModel.newLabData.observe(viewLifecycleOwner, newLabObserver)
    }

    private fun setListeners(view: View) {
        val setDeadlineButton = view.findViewById<MaterialButton>(R.id.setDeadlineButton)
        val setDeadlineText = view.findViewById<TextView>(R.id.setDeadlineText)
        var formateSave = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        var formateView = SimpleDateFormat("dd MMMM yyyy")
        setDeadlineButton.setOnClickListener {
            val now = Calendar.getInstance()
            val datePicker =
                context?.let { it1 ->
                    DatePickerDialog(
                        it1, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                            val selectedDate = Calendar.getInstance()
                            selectedDate.set(Calendar.YEAR, year)
                            selectedDate.set(Calendar.MONTH, month)
                            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                            val date = formateView.format(selectedDate.time)
                            val dateSave = formateSave.format(selectedDate.time)
                            viewModel.deadlineDate = dateSave
                            setDeadlineText.text = "Дедлайн: " + date.toString()
                        },
                        now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
                }
            if (datePicker != null) {
                datePicker.show()
            }
        }
    }

    private fun setUpRecycler() {
        viewBinding.recyclerViewStudClass.let {
            it.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            it.adapter = adapter
        }
    }


    private val labTasksObserver = Observer<List<LabTask>> {
        adapter.clear()
        adapter.addAll(
            it.map { labTask ->
                AddLabListItem(labTask, onAddClicked = { taskLab ->
                    if (viewModel.deadlineDate == null) {
                        apiExceptionObserver.onChanged("Выставите дедлайн выполнение лабораторной работы")
                   }
                    else {
                        val classId = classService.classId ?: return@AddLabListItem
                        if (viewModel.deadlineDate != null) {
                            showInputLabTitleDialog { title ->
                                Timber.i("new title $title")
                                if (title != "")
                                    viewModel.assignLab(title, taskLab.id, classId.toInt(), viewModel.deadlineDate!!)
                                else
                                    apiExceptionObserver.onChanged("Заголов не может быть пустым. Пожалуйста, введите название работы.")
                            }
                        }
                    }
                })
            }
        )
        adapter.notifyDataSetChanged()
    }

    private val newLabObserver = Observer<Lab> {
        navController.popBackStack()
    }

    private fun showInputLabTitleDialog(onInput: (title: String) -> Unit) {
        MaterialDialog.Builder(requireContext())
            .title("Введите название лабораторной работы")
            .inputType(InputType.TYPE_CLASS_TEXT)
            .input(
                "Лабораторная работа", ""
            ) { dialog, input ->
                Timber.i("input $input")
                onInput(input.toString())
            }.show()
    }

}