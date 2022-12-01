package project.st991558097.shubh.workout

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import project.st991558097.shubh.R
import project.st991558097.shubh.data.WorkoutItem
import project.st991558097.shubh.databinding.WorkoutListRowBinding

class WorkoutListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val workoutItems =  mutableListOf<WorkoutItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WorkoutItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as WorkoutItemViewHolder).onBind(workoutItems[position])
    }

    override fun getItemCount(): Int {
        return workoutItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(workoutItems:List<WorkoutItem>?){
        this.workoutItems.clear()
        this.workoutItems.addAll(workoutItems?: emptyList())
        notifyDataSetChanged()
    }

    inner class WorkoutItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.workout_list_row, parent, false)
    ){

        private val binding = WorkoutListRowBinding.bind(itemView)

        fun onBind(workoutItems:WorkoutItem){
            binding.workoutName.text = workoutItems.workoutName
            var image = workoutItems.imageUri
            Log.d("image URL", image)
            Picasso.get().load(image).into(binding.workoutImg)
        }
    }
}