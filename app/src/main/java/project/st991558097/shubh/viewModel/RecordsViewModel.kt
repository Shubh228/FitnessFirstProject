package project.st991558097.shubh.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.st991558097.shubh.data.WorkoutItem
import project.st991558097.shubh.data.WorkoutRecordItem
import project.st991558097.shubh.repository.RecordsRepository
import project.st991558097.shubh.repository.WorkoutRepository

/*
Creator - Shubh Patel(991558097)
RecordsViewModel class will helps us to bind the data and fetch record using the databinding and viewmodel
 */
class RecordsViewModel : ViewModel() {

    private val recordsRepo = RecordsRepository()

    private val _recordLiveData= MutableLiveData<List<WorkoutRecordItem>>()
    val recordLiveData:LiveData<List<WorkoutRecordItem>> = _recordLiveData

    // This viewmodel method which will fetch the data using the RecordsRepository
    fun fetchRecordList(name: String, uri:String){
        recordsRepo.fetchRecordList(_recordLiveData, name, uri)
    }

    //This method will display the recent top 5 workout activities using the RecordsRepository
    fun fetchRecentList(name: String, uri: String){
        recordsRepo.fetchRecentList(_recordLiveData,name, uri)
    }
}