package project.st991558097.shubh.viewModel

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.st991558097.shubh.data.MealItem
import project.st991558097.shubh.repository.DietRepository

class DietViewModel: ViewModel() {
    private val dietRepo = DietRepository()

    private val _dietLiveData = MutableLiveData<List<MealItem>>()
    val dietLiveData:LiveData<List<MealItem>> = _dietLiveData

    fun fetchMealList(){
        dietRepo.fetchMealList(_dietLiveData)
    }
}