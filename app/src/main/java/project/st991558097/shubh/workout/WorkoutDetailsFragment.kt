package project.st991558097.shubh.workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import project.st991558097.shubh.R

class WorkoutDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_workout_details, container, false)

        view.findViewById<Button>(R.id.goToAddRecord).setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_workoutDetailsFragment_to_addRecordFragment)
        }

        view.findViewById<Button>(R.id.goToRecordList).setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_workoutDetailsFragment_to_recordsListFragment)
        }
        return view
    }

}