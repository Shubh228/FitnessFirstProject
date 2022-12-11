package project.st991558097.shubh.workout.workoutAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import project.st991558097.shubh.R

class DayListAdapter(days:List<String>):RecyclerView.Adapter<DayListAdapter.MyViewHolder>() {
    private val dayList = days
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val day: RelativeLayout = itemView.findViewById(R.id.day)
        val dayText:TextView = itemView.findViewById(R.id.daytext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weekly_display_row, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = dayList[position]

        holder.dayText.text = item
        holder.day.setOnClickListener {
            Toast.makeText(it.context, "$item", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int= dayList.size
}