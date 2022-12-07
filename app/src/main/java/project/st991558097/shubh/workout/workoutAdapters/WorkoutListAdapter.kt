package project.st991558097.shubh.workout.workoutAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.st991558097.shubh.R
import project.st991558097.shubh.data.WorkoutItem
import project.st991558097.shubh.databinding.WorkoutListRowBinding
import java.lang.ref.WeakReference

class WorkoutListAdapter(private val callbackWeakReference: WeakReference<WorkoutItemInterface>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface WorkoutItemInterface{
        fun onWorkoutItemClicked(name: String, img:String){

        }
    }
    private val workoutItems =  mutableListOf<WorkoutItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WorkoutItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as WorkoutItemViewHolder).onBind(workoutItems[position]){
            name,img -> callbackWeakReference.get()?.onWorkoutItemClicked(name, img)

        }
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

        fun onBind(workoutItems:WorkoutItem, onClick: (String, String) -> Unit){
            binding.name= workoutItems.workoutName
            binding.image = workoutItems.imageUri

            binding.root.setOnClickListener{
                onClick(workoutItems.workoutName, workoutItems.imageUri)
            }
        }
    }
}