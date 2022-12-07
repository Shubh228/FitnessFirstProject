package project.st991558097.shubh.workout.workoutAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import project.st991558097.shubh.R
import project.st991558097.shubh.data.WorkoutRecordItem
import project.st991558097.shubh.databinding.RecordsDisplayRowBinding
import project.st991558097.shubh.workout.WorkoutActivity
import java.lang.ref.WeakReference


class RecordsListAdapter(private val callbackWeakReference: WeakReference<RecordInterface>):  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface RecordInterface{
        fun onRecordClicked(name: String, img:String){

        }
    }

    private val recordItems =  mutableListOf<WorkoutRecordItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecordItemViewHolder(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RecordItemViewHolder).onBind(recordItems[position]){
                name,img -> callbackWeakReference.get()?.onRecordClicked(name, img)

        }    }

    override fun getItemCount(): Int {
        return recordItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteItem(pos:Int){
        val email = Firebase.auth.currentUser?.email
        val username = email?.split("@")?.get(0).toString()
        FirebaseDatabase.getInstance().getReference("Users").child(username).child("Records").child(recordItems[pos].name).child(recordItems[pos].id).removeValue()

        notifyDataSetChanged()
    }

    fun setItem(pos: Int){
        var item = recordItems[pos]

            WorkoutActivity.editRecord.id = item.id
            WorkoutActivity.editRecord.date = item.date
            WorkoutActivity.editRecord.img = item.img
            WorkoutActivity.editRecord.name = item.name
            WorkoutActivity.editRecord.duration = item.duration
            WorkoutActivity.editRecord.endTime = item.endTime
            WorkoutActivity.editRecord.feedback = item.feedback
            WorkoutActivity.editRecord.rating = item.rating
            WorkoutActivity.editRecord.startTime = item.startTime

    }

  /*  @SuppressLint("NotifyDataSetChanged")
    fun delRecord(recordItem:WorkoutRecordItem){
        this.recordItems.clear()
        this.recordItems.addAll(recordItems?: emptyList())
        notifyDataSetChanged()
    }*/

    @SuppressLint("NotifyDataSetChanged")
    fun setRecords(recordItems:List<WorkoutRecordItem>?){
        this.recordItems.clear()
        this.recordItems.addAll(recordItems?: emptyList())
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setRecordList(recordItems:List<WorkoutRecordItem>?){
        this.recordItems.clear()
        this.recordItems.addAll(recordItems?: emptyList())
        notifyDataSetChanged()
    }

    inner class RecordItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.records_display_row, parent, false)
    ){

        private val binding = RecordsDisplayRowBinding.bind(itemView)

        fun onBind(recordItem:WorkoutRecordItem, onClick: (String, String) -> Unit){
            binding.name= recordItem.name
            binding.date = recordItem.date
            binding.start = recordItem.startTime
            binding.end = recordItem.endTime
            binding.duration = recordItem.duration
            binding.img = recordItem.img
            binding.rating = recordItem.rating


            binding.root.setOnClickListener{
                onClick(recordItem.name, recordItem.img)
            }

        }
    }
}