package project.st991558097.shubh.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.st991558097.shubh.data.WorkoutItem
import project.st991558097.shubh.data.WorkoutRecordItem
import project.st991558097.shubh.repository.RecordsRepository
import project.st991558097.shubh.repository.WorkoutRepository

class RecordsViewModel : ViewModel() {

    private val recordsRepo = RecordsRepository()

    private val _recordLiveData= MutableLiveData<List<WorkoutRecordItem>>()
    val recordLiveData:LiveData<List<WorkoutRecordItem>> = _recordLiveData

    fun fetchRecordList(name: String, uri:String){
        recordsRepo.fetchRecordList(_recordLiveData, name, uri)
    }
}