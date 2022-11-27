package project.st991558097.shubh.onboarding

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import project.st991558097.shubh.R

class SelectionFragment : Fragment() {

    private var workoutsList:ArrayList<workoutItem>?=null
    private lateinit var username:String

    private lateinit var database: FirebaseDatabase
    private lateinit var dbRef: DatabaseReference
    private lateinit var userRef:DatabaseReference
    private lateinit var recView: RecyclerView
    private lateinit var nextPage:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_selection, container, false)

        //Initializing late init variables
        database = FirebaseDatabase.getInstance()
        dbRef = database.getReference("Workouts")
        workoutsList = ArrayList()
        recView = view.findViewById(R.id.workoutRecView)
        nextPage = view.findViewById(R.id.nextPageBtn)
        userRef = database.getReference("Users")

        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email
            username = email?.split("@")?.get(0).toString()
        }

        //Attaching linear layout manager to the recycler view
        recView.layoutManager = LinearLayoutManager(this.context)
        recView.setHasFixedSize(true)

        //Fetching data from Firebase and displaying it
        dbRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()){
                    for (item in snapshot.children){
                        val name = item.key.toString()
                        val image = item.child("Image").value.toString()
                        Log.d("NAME", name)
                        println(name)
                        println(image)
                        workoutsList!!.add(workoutItem(image, name))
                    }
                }
                recView.adapter = OnboardingAdapter(workoutsList!! as List<workoutItem>)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        //Going to next page
        nextPage.setOnClickListener {
            userRef.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.child(username).hasChild("Workouts")){
                        Navigation.findNavController(requireView()).navigate(R.id.action_selectionFragment_to_signInFragment2)
                        Toast.makeText(context, "Please log in", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(context, "Please select atleast one workout.", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
        return view
    }
    
}