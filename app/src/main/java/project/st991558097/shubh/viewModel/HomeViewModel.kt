package project.st991558097.shubh.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.st991558097.shubh.data.User
import project.st991558097.shubh.data.WorkoutItem
import project.st991558097.shubh.repository.DietRepository
import project.st991558097.shubh.repository.WorkoutRepository

class HomeViewModel: ViewModel() {

    private val workoutRepo = WorkoutRepository()
    private val dietRepo = DietRepository()

    val _userName: MutableLiveData<String> = MutableLiveData("")
    val userName : LiveData<String> = _userName
    val _target: MutableLiveData<String> = MutableLiveData("")
    val target: LiveData<String> = _target
    val _consumed: MutableLiveData<String> = MutableLiveData("")
    val consumed:LiveData<String> = _consumed
    private val _workoutLiveData= MutableLiveData<List<WorkoutItem>>()
    val workoutLiveData: LiveData<List<WorkoutItem>> = _workoutLiveData

    fun fetchWorkoutList(){
        workoutRepo.fetchWorkoutList(_workoutLiveData)
    }

    fun fetchUserName(){
        dietRepo.fetchUserName(_userName)
    }

    fun fetchTargetCalories(){
        dietRepo.getTargetCalories(_target)
    }

    fun fetchConsumedCalories(){
        dietRepo.getConsumedCalories(_consumed)
    }


}