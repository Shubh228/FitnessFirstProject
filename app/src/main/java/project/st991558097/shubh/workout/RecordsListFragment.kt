package project.st991558097.shubh.workout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.st991558097.shubh.R
import project.st991558097.shubh.data.WorkoutRecordItem
import project.st991558097.shubh.databinding.FragmentRecordsListBinding
import project.st991558097.shubh.databinding.FragmentWorkoutDetailsBinding
import project.st991558097.shubh.viewModel.RecordsViewModel
import java.lang.ref.WeakReference

class RecordsListFragment : Fragment(), RecordsListAdapter.RecordInterface {

    private var _binding: FragmentRecordsListBinding? = null
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
        _binding = FragmentRecordsListBinding.inflate(inflater, container, false)

        binding.noListView.visibility = View.GONE
        workoutName = activity?.intent?.getStringExtra(WorkoutActivity.ARG_NAME).toString()
        workoutImg = activity?.intent?.getStringExtra(WorkoutActivity.ARG_IMG).toString()

        val recordsListAdapter = RecordsListAdapter(WeakReference(this))
        binding.recordsListRV.adapter = recordsListAdapter
        binding.recordsListRV.layoutManager = LinearLayoutManager(this.context)

        recordViewModel.fetchRecordList(workoutName, workoutImg)
        recordViewModel.recordLiveData.observe(this.viewLifecycleOwner){
                recordItem -> recordsListAdapter.setRecordList(recordItem)
            if (recordItem.isEmpty()){
                binding.recordsListRV.visibility = View.GONE
                binding.noListView.visibility = View.VISIBLE
            }else{
                binding.recordsListRV.visibility = View.VISIBLE
                binding.noListView.visibility = View.GONE
            }
        }
        return binding.root
    }

    @BindingAdapter("setRecordList")
    fun setRecordList(recyclerView: RecyclerView, list:List<WorkoutRecordItem>?){
        (recyclerView.adapter as RecordsListAdapter).setRecords(list)
    }

}