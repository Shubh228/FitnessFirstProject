package project.st991558097.shubh.onboarding

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import project.st991558097.shubh.R

class OnboardingAdapter(private val workouts:List<workoutItem>): RecyclerView.Adapter<OnboardingAdapter.MyViewHolder>(){

    var workoutList: ArrayList<String> = arrayListOf()
    private lateinit var username:String
    val user = Firebase.auth.currentUser
    private var dbReference: DatabaseReference = Firebase.database.reference

    class MyViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){
        val imageV:ImageView = itemview.findViewById(R.id.workoutImg)
        val nameV:TextView = itemview.findViewById(R.id.workoutName)
        val cardView: RelativeLayout = itemview.findViewById(R.id.workoutCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.workout_display_row,parent,false)
        return MyViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: workoutItem = workouts[position]

        getUserInfo()
        Picasso.get().load(item.imageUri).into(holder.imageV)
        holder.nameV.text = item.workoutName
        holder.itemView.setOnClickListener {

            if (holder.cardView.isSelected){
                holder.cardView.setBackgroundColor(Color.parseColor("#FFFFFF"))
                holder.cardView.isSelected = false
                if (workoutList.contains(item.workoutName)){
                    workoutList.remove(item.workoutName)
                }
                addWorkout()
            }
            else{
                workoutList.add(item.workoutName)

                holder.cardView.isSelected = true
                holder.cardView.setBackgroundColor(Color.parseColor("#ADD8E6"))
                addWorkout()

            }
        }
    }

    private fun getUserInfo() {
        user?.let {
            val email = user.email
            username = email?.split("@")?.get(0).toString()
        }
    }


    private fun addWorkout() {
        dbReference.child("Users").child(username).child("Workouts").setValue(workoutList)
    }

    override fun getItemCount(): Int {
        return workouts.size
    }

}