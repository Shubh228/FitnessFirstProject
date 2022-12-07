package project.st991558097.shubh.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.st991558097.shubh.R
import project.st991558097.shubh.data.WorkoutItem
import project.st991558097.shubh.viewModel.WorkoutViewModel
import project.st991558097.shubh.workout.WorkoutActivity
import project.st991558097.shubh.workout.workoutAdapters.WorkoutListAdapter
import java.lang.ref.WeakReference

class HomeFragment : Fragment(), WorkoutListAdapter.WorkoutItemInterface {

    private val viewModel: WorkoutViewModel by lazy {
        ViewModelProvider(requireActivity())[WorkoutViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //val binding = DataBindingUtil.setContentView<ViewDataBinding>(requireActivity(), R.layout.fragment_home)
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //binding.lifecycleOwner= this

        val workoutListAdapter = WorkoutListAdapter(WeakReference(this))
        view.findViewById<RecyclerView>(R.id.homeRecyclerView).adapter = workoutListAdapter
        view.findViewById<RecyclerView>(R.id.homeRecyclerView).layoutManager = LinearLayoutManager(this.context)


        viewModel.fetchWorkoutList()
        viewModel.workoutLiveData.observe(this.viewLifecycleOwner){
            workoutItem -> workoutListAdapter.setItems(workoutItem)
            Log.d("From home fragment", workoutItem.toString())
        }
        return view
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