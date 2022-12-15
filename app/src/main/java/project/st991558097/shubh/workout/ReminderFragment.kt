package project.st991558097.shubh.workout

import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import project.st991558097.shubh.R
import project.st991558097.shubh.data.Reminder
import project.st991558097.shubh.databinding.FragmentReminderBinding
import project.st991558097.shubh.viewModel.ReminderViewModel
import project.st991558097.shubh.workout.workoutAdapters.RecordsListAdapter
import project.st991558097.shubh.workout.workoutAdapters.ReminderListAdapter
import java.lang.ref.WeakReference
/*
Creator - Purv Patel(991558098)
ReminderFragement class will display the data to the user using live data and view model
 */

class ReminderFragment : Fragment() {
    private var _binding: FragmentReminderBinding? = null
    private val binding get() = _binding!!
    private val deleteImage = R.drawable.icon_trash

    private val reminderViewModel : ReminderViewModel by lazy {
        ViewModelProvider(requireActivity())[ReminderViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentReminderBinding.inflate(inflater, container, false)

        binding.noListText.visibility = View.GONE
        val reminderListAdapter = ReminderListAdapter()
        binding.reminderListRV.adapter = reminderListAdapter
        binding.reminderListRV.layoutManager = LinearLayoutManager(this.context)

        binding.goToHome.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_reminderFragment_to_homeFragment)
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
                when (direction) {
                    ItemTouchHelper.RIGHT -> {
                        reminderListAdapter.deleteItem(viewHolder.adapterPosition)
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

        touch.attachToRecyclerView(binding.reminderListRV)

        reminderViewModel.fetchReminderList()
        reminderViewModel.reminderLiveData.observe(this.viewLifecycleOwner){
                reminderItem -> reminderListAdapter.setReminder(reminderItem)
            if (reminderItem.isEmpty()){
                binding.reminderListRV.visibility = View.GONE
                binding.noListText.visibility = View.VISIBLE
            }else{
                binding.reminderListRV.visibility = View.VISIBLE
                binding.noListText.visibility = View.GONE
            }
        }
        return binding.root
    }

}