package project.st991558097.shubh.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import project.st991558097.shubh.data.Reminder
import project.st991558097.shubh.data.WorkoutItem

/*
Creator - Purv Patel(991558098)
This repository will help us to fetch the reminder list for the user from the database
 */
class RemindersRepository {
    private val user = Firebase.auth.currentUser
    private val email = user!!.email
    private val username = email?.split("@")?.get(0).toString()
    private val database= Firebase.database
    private val dbRef = database.getReference("Users").child(username).child("Reminders")

    fun fetchReminderList(liveData: MutableLiveData<List<Reminder>>){
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val reminderItems: ArrayList<Reminder> = arrayListOf()
                for(data in snapshot.children){
                    val name = data.child("workoutName").value.toString()
                    val img_url = data.child("workoutImg").value.toString()
                    val time = data.child("reminderTime").value.toString()
                    val date = data.child("reminderDate").value.toString()
                    reminderItems.add(Reminder(data.key!!, name, img_url, time, date))
                }
                liveData.postValue(reminderItems as List<Reminder>)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}