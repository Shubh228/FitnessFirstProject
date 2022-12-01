package project.st991558097.shubh.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.st991558097.shubh.data.WorkoutItem
import project.st991558097.shubh.workout.WorkoutRepository

class WorkoutViewModel : ViewModel() {

    private val workoutRepo = WorkoutRepository()

    private val _workoutLiveData= MutableLiveData<List<WorkoutItem>>()
    val workoutLiveData:LiveData<List<WorkoutItem>> = _workoutLiveData

    fun fetchWorkoutList(){
        workoutRepo.fetchWorkoutList(_workoutLiveData)
    }

    //private val _name = MutableLiveData<String>("")


}