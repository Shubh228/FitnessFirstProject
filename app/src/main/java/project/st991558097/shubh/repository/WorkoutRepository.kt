package project.st991558097.shubh.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import project.st991558097.shubh.data.WorkoutItem

/*
Creator - Shubh Patel(991558097)
 */
class WorkoutRepository {

    private val user = Firebase.auth.currentUser
    private val email = user!!.email
    private val username = email?.split("@")?.get(0).toString()
    private val database= Firebase.database
    private val dbRef = database.getReference("Users").child(username).child("Workouts")

    //Will display the workout list activities that user has enrolled in while signing up for our application
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