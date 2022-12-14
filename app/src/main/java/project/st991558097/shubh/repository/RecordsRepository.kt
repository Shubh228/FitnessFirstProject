package project.st991558097.shubh.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import project.st991558097.shubh.data.WorkoutItem
import project.st991558097.shubh.data.WorkoutRecordItem
import project.st991558097.shubh.workout.WorkoutActivity.Companion.ARG_IMG
import project.st991558097.shubh.workout.WorkoutActivity.Companion.ARG_NAME
import project.st991558097.shubh.workout.WorkoutDetailsFragment

/*
Creator - Shubh Patel(991558097)
RecordsRepository class helps us to fetch/get workout record from the firebase will display it to the user
 */
class RecordsRepository {
    private val user = Firebase.auth.currentUser
    private val email = user!!.email
    private val username = email?.split("@")?.get(0).toString()
    private val database= Firebase.database
    private val image = ARG_IMG
    private val dbRef = database.getReference("Users").child(username).child("Records")

    //Below method will get the workout records from our the firebase database
    fun fetchRecordList(liveData: MutableLiveData<List<WorkoutRecordItem>>, name:String, uri:String){
        dbRef.child(name).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val records: ArrayList<WorkoutRecordItem> = arrayListOf()
                for(data in snapshot.children){
                    val name = data.child("name").value.toString()
                    val date = data.child("date").value.toString()
                    val startTime = data.child("startTime").value.toString()
                    val endTime = data.child("endTime").value.toString()
                    val duration = data.child("duration").value.toString()
                    var rating = data.child("rating").value.toString()
                    var feedback = data.child("feedback").value.toString()
                    Log.d("key", data.key!!)
                    records.add(WorkoutRecordItem(data.key!!, name,date, startTime, endTime, duration, rating, feedback, uri))
                }
                liveData.postValue(records as List<WorkoutRecordItem>)
                Log.d("Records", records.toString())
                Log.d("Image", uri)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    fun fetchRecentList(liveData: MutableLiveData<List<WorkoutRecordItem>>, name:String, uri:String){
        dbRef.child(name).limitToFirst(5).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val records: ArrayList<WorkoutRecordItem> = arrayListOf()
                for(data in snapshot.children){
                    val name = data.child("name").value.toString()
                    val date = data.child("date").value.toString()
                    val startTime = data.child("startTime").value.toString()
                    val endTime = data.child("endTime").value.toString()
                    val duration = data.child("duration").value.toString()
                    var rating = data.child("rating").value.toString()
                    var feedback = data.child("feedback").value.toString()
                    Log.d("key", data.key!!)
                    records.add(WorkoutRecordItem(data.key!!, name,date, startTime, endTime, duration, rating, feedback, uri))
                }
                liveData.postValue(records as List<WorkoutRecordItem>)
                Log.d("Records", records.toString())
                Log.d("Image", uri)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

}