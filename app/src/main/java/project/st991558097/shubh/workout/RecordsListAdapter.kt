package project.st991558097.shubh.workout

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.st991558097.shubh.R
import project.st991558097.shubh.data.WorkoutRecordItem
import project.st991558097.shubh.databinding.RecordsDisplayRowBinding
import java.lang.ref.WeakReference

class RecordsListAdapter(private val callbackWeakReference: WeakReference<RecordsListAdapter.RecordInterface>):  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface RecordInterface{
        fun onRecordClicked(name: String, img:String){

        }
    }

    private val recordItems =  mutableListOf<WorkoutRecordItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecordItemViewHolder(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RecordsListAdapter.RecordItemViewHolder).onBind(recordItems[position]){
                name,img -> callbackWeakReference.get()?.onRecordClicked(name, img)

        }    }

    override fun getItemCount(): Int {
        return recordItems.size
    }

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
        this.recordItems.addAll(recordItems?: emptyList())
        this.recordItems.addAll(recordItems?: emptyList())
        this.recordItems.addAll(recordItems?: emptyList())
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