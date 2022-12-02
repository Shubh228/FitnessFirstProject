package project.st991558097.shubh.workout

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import project.st991558097.shubh.data.UserWorkoutItem
import project.st991558097.shubh.data.WorkoutItem
import java.time.Duration
import kotlin.math.log

class WorkoutRepository {

    private val user = Firebase.auth.currentUser
    private val email = user!!.email
    private val username = email?.split("@")?.get(0).toString()
    private val database= Firebase.database
    private val dbRef = database.getReference("Users").child(username).child("Workouts")

    fun fetchWorkoutList(liveData: MutableLiveData<List<WorkoutItem>>){
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val workoutItems: ArrayList<WorkoutItem> = arrayListOf()
                for(data in snapshot.children){
                    val name = data.child("workoutName").value.toString()
                    val img_url = data.child("imageUri").value.toString()
                    workoutItems.add(WorkoutItem(name, img_url))
                }
                liveData.postValue(workoutItems as List<WorkoutItem>)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }



}