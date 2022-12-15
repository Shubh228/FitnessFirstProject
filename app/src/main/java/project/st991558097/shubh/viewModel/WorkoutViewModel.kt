package project.st991558097.shubh.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.st991558097.shubh.data.WorkoutItem
import project.st991558097.shubh.repository.WorkoutRepository

class WorkoutViewModel : ViewModel() {

    private val workoutRepo = WorkoutRepository()

    private val _workoutLiveData= MutableLiveData<List<WorkoutItem>>()
    val workoutLiveData:LiveData<List<WorkoutItem>> = _workoutLiveData

    //This method will display the workout record using the WorkoutRepository
    fun fetchWorkoutList(){
        workoutRepo.fetchWorkoutList(_workoutLiveData)
    }
}