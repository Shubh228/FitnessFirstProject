package project.st991558097.shubh.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import project.st991558097.shubh.data.Diet
import project.st991558097.shubh.home.FFHomeActivity

class DietRepository {

    private val user = Firebase.auth.currentUser
    private val email = user!!.email
    private val username = email?.split("@")?.get(0).toString()
    private val database= Firebase.database
    private val dbRef = database.getReference("Users").child(username)

    fun fetchUserName(liveData: MutableLiveData<String>){
        dbRef.addValueEventListener(object : ValueEventListener{
            var name:String = ""
            override fun onDataChange(snapshot: DataSnapshot) {
                name = snapshot.child("name").value.toString()
                liveData.postValue(name)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getTargetCalories(liveData: MutableLiveData<String>){
        dbRef.addValueEventListener(object : ValueEventListener {
            var target:String = ""
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.child("Information")
                target = data.child("targetCalories").value.toString()
                liveData.postValue(target)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getConsumedCalories(liveData: MutableLiveData<String>) {
        dbRef.child("Diet").child("todaysDate").addValueEventListener(object : ValueEventListener{
            var consumed:String=""
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!=null){
                    consumed = snapshot.child("consumedCalories").value.toString()
                    liveData.postValue(consumed)
                }
                else{
                    consumed = "0"
                    liveData.postValue(consumed)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}