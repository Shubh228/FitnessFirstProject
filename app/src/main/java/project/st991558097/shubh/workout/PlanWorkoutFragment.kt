package project.st991558097.shubh.workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.st991558097.shubh.R
import project.st991558097.shubh.databinding.FragmentPlanWorkoutBinding
import project.st991558097.shubh.workout.workoutAdapters.DayListAdapter

class PlanWorkoutFragment : Fragment() {

    private var days: List<String> = listOf("Sun", "Mon", "Tue", "Wed", "Thurs", "Fri", "Sat")
    private var _binding: FragmentPlanWorkoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var workoutName:String
    private lateinit var workoutImg:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPlanWorkoutBinding.inflate(inflater, container, false)
        workoutName = activity?.intent?.getStringExtra(WorkoutActivity.ARG_NAME).toString()
        workoutImg = activity?.intent?.getStringExtra(WorkoutActivity.ARG_IMG).toString()

        binding.name = workoutName
        binding.image = workoutImg
        binding.weekRCView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.weekRCView.adapter = DayListAdapter(days)



        // Inflate the layout for this fragment
        return binding.root
    }
}