package project.st991558097.shubh.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.st991558097.shubh.data.Reminder
import project.st991558097.shubh.data.WorkoutItem
import project.st991558097.shubh.repository.RemindersRepository
import project.st991558097.shubh.repository.WorkoutRepository

/*
Creator - Purv Patel(991558098)
ReminderViewModel class will helps us to bind the data and fetch workout plan using the databinding and viewmodel
 */
class ReminderViewModel: ViewModel() {
    private val reminderRepo = RemindersRepository()

    private val _reminderLiveData= MutableLiveData<List<Reminder>>()
    val reminderLiveData: LiveData<List<Reminder>> = _reminderLiveData

    //This method will display the reminders that user has set using the ReminderRepository
    fun fetchReminderList(){
        reminderRepo.fetchReminderList(_reminderLiveData)
    }
}