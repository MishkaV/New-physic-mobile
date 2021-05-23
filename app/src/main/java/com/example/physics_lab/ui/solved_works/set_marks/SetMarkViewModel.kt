package com.example.physics_lab.ui.solved_works.set_marks

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.physics_lab.data.ActiveSolutionData
import com.example.physics_lab.data.ClassRoomItem
import com.example.physics_lab.data.SolutionItemData
import com.example.physics_lab.network.ClassRepository
import com.example.physics_lab.network.LabRepository
import com.example.physics_lab.service.AuthService
import com.example.physics_lab.service.ClassService
import com.example.physics_lab.service.LabService
import com.example.physics_lab.service.SolutionService
import com.example.physics_lab.ui._base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SetMarkViewModel(context: Context) : BaseViewModel() {
    private val authService = AuthService(context)
    private val solutionService = SolutionService(context)
    private val labService = LabService(context)
    val activeSolution = MutableLiveData<SolutionItemData>()
    val mark = MutableLiveData<String>()

    fun setMarkResponse(grade : Int) {
        val token = authService.token ?: return
        val labId = labService.labId ?: return
        val userId = solutionService.checkedUserId ?: return
        scopeMain.launch {
            val response = withContext(Dispatchers.IO) {
                ClassRepository.putMarkStudent(token, labId, grade, userId)
            }
            if(response == null){
                apiExceptionData.value = "Невозможно получить запрос"
            }
            else {
                activeSolution.value = response
            }
        }
    }

}