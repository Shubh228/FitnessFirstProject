package project.st991558097.shubh.workout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import project.st991558097.shubh.R
import project.st991558097.shubh.data.WorkoutRecordItem
import project.st991558097.shubh.databinding.FragmentWorkoutDetailsBinding
import project.st991558097.shubh.viewModel.RecordsViewModel
import project.st991558097.shubh.workout.WorkoutActivity.Companion.ARG_IMG
import project.st991558097.shubh.workout.WorkoutActivity.Companion.ARG_NAME
import project.st991558097.shubh.workout.workoutAdapters.RecordsListAdapter
import java.lang.ref.WeakReference

class WorkoutDetailsFragment : Fragment(), RecordsListAdapter.RecordInterface {

    private var _binding: FragmentWorkoutDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var workoutName:String
    private lateinit var workoutImg:String

    private val recordViewModel: RecordsViewModel by lazy {
        ViewModelProvider(requireActivity())[RecordsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        _binding = FragmentWorkoutDetailsBinding.inflate(inflater, container, false)

        binding.noListText.visibility = View.GONE
        workoutName = activity?.intent?.getStringExtra(ARG_NAME).toString()
        workoutImg = activity?.intent?.getStringExtra(ARG_IMG).toString()
        displayItems()

        val recordsListAdapter = RecordsListAdapter(WeakReference(this))
        binding.recordsRCView.adapter = recordsListAdapter
        binding.recordsRCView.layoutManager = LinearLayoutManager(this.context)

        binding.showRecords.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_workoutDetailsFragment_to_recordsListFragment)
        }

        binding.goToAddRecord.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_workoutDetailsFragment_to_addRecordFragment)
        }

        binding.goToPlanWorkout.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_workoutDetailsFragment_to_planWorkoutFragment)
        }

        recordViewModel.fetchRecentList(workoutName, workoutImg)
        recordViewModel.recordLiveData.observe(this.viewLifecycleOwner){
            recordItem -> recordsListAdapter.setRecords(recordItem)
            if (recordItem.isEmpty()){
                binding.recordsRCView.visibility = View.GONE
                binding.noListText.visibility = View.VISIBLE
            }else{
                binding.recordsRCView.visibility = View.VISIBLE
                binding.noListText.visibility = View.GONE
            }
            Log.d("Records", recordItem.toString())
        }
        return binding.root
    }

    private fun displayItems() {
        binding.workoutName.text = workoutName
        Picasso.get().load(workoutImg).into(binding.workoutImg)
    }

    @BindingAdapter("setRecords")
    fun setRecords(recyclerView: RecyclerView, list:List<WorkoutRecordItem>?){
        (recyclerView.adapter as RecordsListAdapter).setRecords(list)
    }

    override fun onRecordClicked(name: String, img: String) {
        super.onRecordClicked(name, img)

    }
}