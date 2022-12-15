package project.st991558097.shubh.workout

import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.st991558097.shubh.R
import project.st991558097.shubh.data.WorkoutRecordItem
import project.st991558097.shubh.databinding.FragmentRecordsListBinding
import project.st991558097.shubh.viewModel.RecordsViewModel
import java.lang.ref.WeakReference
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import project.st991558097.shubh.workout.workoutAdapters.RecordsListAdapter


class RecordsListFragment : Fragment(), RecordsListAdapter.RecordInterface {

    private var _binding: FragmentRecordsListBinding? = null
    private val binding get() = _binding!!
    private lateinit var workoutName:String
    private lateinit var workoutImg:String
//    private val deleteColor = ContextCompat.getColor()
    private val editColor = R.color.yellow
    private val deleteImage = R.drawable.icon_trash
    private val editImage = R.drawable.icon_edit

    private val recordViewModel: RecordsViewModel by lazy {
        ViewModelProvider(requireActivity())[RecordsViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentRecordsListBinding.inflate(inflater, container, false)

        binding.noListText.visibility = View.GONE
        workoutName = activity?.intent?.getStringExtra(WorkoutActivity.ARG_NAME).toString()
        workoutImg = activity?.intent?.getStringExtra(WorkoutActivity.ARG_IMG).toString()


        val recordsListAdapter = RecordsListAdapter(WeakReference(this))
        binding.recordsListRV.adapter = recordsListAdapter
        binding.recordsListRV.layoutManager = LinearLayoutManager(this.context)

        binding.goToAddRecord.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_recordsListFragment_to_addRecordFragment2)
        }

        val touch = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction){
                    ItemTouchHelper.RIGHT->{
                        recordsListAdapter.deleteItem(viewHolder.adapterPosition)
                    }
                    ItemTouchHelper.LEFT -> {
                        recordsListAdapter.setItem(viewHolder.adapterPosition)
                        val bundle = Bundle()
                        bundle.putBoolean("edit", true)
                        Navigation.findNavController(requireView()).navigate(R.id.action_recordsListFragment_to_addRecordFragment2, bundle)
                    }
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(context!!, R.color.yellow))
                    .addSwipeLeftActionIcon(R.drawable.icon_edit)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(context!!, R.color.red))
                    .addSwipeRightActionIcon(R.drawable.icon_trash)
                    .create()
                    .decorate()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        })

        touch.attachToRecyclerView(binding.recordsListRV)

        recordViewModel.fetchRecordList(workoutName, workoutImg)
        recordViewModel.recordLiveData.observe(this.viewLifecycleOwner){
                recordItem -> recordsListAdapter.setRecordList(recordItem)
            if (recordItem.isEmpty()){
                binding.recordsListRV.visibility = View.GONE
                binding.noListText.visibility = View.VISIBLE
            }else{
                binding.recordsListRV.visibility = View.VISIBLE
                binding.noListText.visibility = View.GONE
            }
        }

        return binding.root
    }

    //Using the RecordList adapter its binding the recycler card view and list for the workout records
    @BindingAdapter("setRecordList")
    fun setRecordList(recyclerView: RecyclerView, list:List<WorkoutRecordItem>?){
        (recyclerView.adapter as RecordsListAdapter).setRecords(list)
    }

}