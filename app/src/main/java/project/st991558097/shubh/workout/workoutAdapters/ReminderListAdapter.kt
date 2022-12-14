package project.st991558097.shubh.workout.workoutAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import project.st991558097.shubh.R
import project.st991558097.shubh.data.Reminder
import project.st991558097.shubh.data.WorkoutRecordItem
import project.st991558097.shubh.databinding.RecordsDisplayRowBinding
import project.st991558097.shubh.databinding.ReminderRowBinding
import project.st991558097.shubh.workout.WorkoutActivity
import java.lang.ref.WeakReference

/*
Creator - Purv Patel(991558098)
This list adapter help us to to create the recycler view for the workout plan list
 */
class ReminderListAdapter :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val reminderItems = mutableListOf<Reminder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ReminderItemViewHolder(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ReminderItemViewHolder).onBind(reminderItems[position])
    }

    override fun getItemCount(): Int {
        return reminderItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteItem(pos: Int) {
        val email = Firebase.auth.currentUser?.email
        val username = email?.split("@")?.get(0).toString()
        FirebaseDatabase.getInstance().getReference("Users").child(username).child("Reminders").child(reminderItems[pos].id).removeValue()

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setReminder(recordItems: List<Reminder>?) {
        this.reminderItems.clear()
        this.reminderItems.addAll(recordItems ?: emptyList())
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setReminderList(recordItems: List<Reminder>?) {
        this.reminderItems.clear()
        this.reminderItems.addAll(reminderItems ?: emptyList())
        notifyDataSetChanged()
    }

    inner class ReminderItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.reminder_row, parent, false)
    ) {

        private val binding = ReminderRowBinding.bind(itemView)

        fun onBind(reminderItem: Reminder) {
            binding.name = reminderItem.workoutName
            binding.date = reminderItem.reminderDate
            binding.time = reminderItem.reminderTime
            binding.img = reminderItem.workoutImg
        }
    }
}