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
import project.st991558097.shubh.data.MealItem
import project.st991558097.shubh.home.FFHomeActivity
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class DietRepository {

    private val user = Firebase.auth.currentUser
    private val email = user!!.email
    private val username = email?.split("@")?.get(0).toString()
    private val database= Firebase.database
    private val dbRef = database.getReference("Users").child(username)

    val current: LocalDate = LocalDate.now()
    val formatter = SimpleDateFormat("yyyy/MM/dd")
    val date = Date()
    val today: String = formatter.format(date)
    val key = today.split("/")[0] + today.split("/")[1] + today.split("/")[2]


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
        dbRef.child("Diet").child(key).addValueEventListener(object : ValueEventListener{
            //var consumed:String=""
            var cals = 0
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children){
                    cals += data.child("calories").value.toString().toInt()
                }
                liveData.postValue(cals.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                liveData.postValue("0")
            }

        })
    }

    fun fetchMealList(liveData: MutableLiveData<List<MealItem>>) {
        dbRef.child("Diet").child(key).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val mealList: ArrayList<MealItem> = arrayListOf()
                for (data in snapshot.children){
                    val name = data.child("mealName").value.toString()
                    val cals = data.child("calories").value.toString()
                    mealList.add(MealItem("", data.key!!, name, cals.toString().toInt()))
                }
                liveData.postValue(mealList as List<MealItem>)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}