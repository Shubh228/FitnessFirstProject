package project.st991558097.shubh.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.st991558097.shubh.R
import project.st991558097.shubh.data.WorkoutItem
import project.st991558097.shubh.databinding.FragmentHomeBinding
import project.st991558097.shubh.diet.DietActivity
import project.st991558097.shubh.viewModel.HomeViewModel
import project.st991558097.shubh.viewModel.WorkoutViewModel
import project.st991558097.shubh.workout.WorkoutActivity
import project.st991558097.shubh.workout.workoutAdapters.WorkoutListAdapter
import java.lang.ref.WeakReference

/*
Creator - Shubh Patel(991558097)
This is homepage activity for our application where we displays the list of different workout that we offer in our application
 */
class HomeFragment : Fragment(), WorkoutListAdapter.WorkoutItemInterface {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() =  _binding!!
    private var cals:String = ""
    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val workoutListAdapter = WorkoutListAdapter(WeakReference(this))
        binding.homeRecyclerView.adapter = workoutListAdapter
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

        binding.viewMealRecords.setOnClickListener {
            var intent = Intent(context, DietActivity::class.java).apply {
                putExtra(DietActivity.ARG_TARGET, cals)
            }
            startActivity(intent)
        }


        homeViewModel.fetchWorkoutList()
        homeViewModel.workoutLiveData.observe(this.viewLifecycleOwner){
            workoutItem -> workoutListAdapter.setItems(workoutItem)
        }

        homeViewModel.fetchUserName()
        homeViewModel.userName.observe(this.viewLifecycleOwner){
            it -> binding.greeting = "Hi $it \nWelcome back! "
        }

        homeViewModel.fetchTargetCalories()
        homeViewModel.target.observe(this.viewLifecycleOwner){
            binding.target = it
            cals = it
        }

        homeViewModel.fetchConsumedCalories()
        homeViewModel.consumed.observe(this.viewLifecycleOwner){
            it-> /*val value = Integer.parseInt(it)
            when(value){
                0 -> binding.mealCount.text = "0"
                in 1..500 -> binding.mealCount.text = "1"
                in 501..1000 -> binding.mealCount.text = "2"
                in 1001..1500 -> binding.mealCount.text = "3"
                in 1501..2000 -> binding.mealCount.text = "4"
            }*/
            binding.completed = it

        }

        return binding.root
    }

    override fun onWorkoutItemClicked(name: String, img:String) {
        var intent = Intent(context, WorkoutActivity::class.java).apply {
            putExtra(WorkoutActivity.ARG_NAME, name)
            putExtra(WorkoutActivity.ARG_IMG, img)
        }
        startActivity(intent)
    }

    @BindingAdapter("setItems")
    fun setItems(recyclerView: RecyclerView, list:List<WorkoutItem>?){
        (recyclerView.adapter as WorkoutListAdapter).setItems(list)
    }

}