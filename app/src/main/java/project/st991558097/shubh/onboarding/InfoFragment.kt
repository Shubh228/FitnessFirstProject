package project.st991558097.shubh.onboarding

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import project.st991558097.shubh.R
import project.st991558097.shubh.data.UserInformation

/*
Creator - Shubh Patel(991558097)
InfoFragment will collect the user information, type of workout user want to start with
 */
class InfoFragment : Fragment() {

    var activities = arrayOf("Beginner - 1 or 2 times a week.", "Intermediate - 3 to 4 times a week", "Expert - 5 to 6 times a week.")
    private lateinit var userName:String
    private lateinit var age:EditText
    private lateinit var height:EditText
    private lateinit var weight:EditText
    private lateinit var gender:EditText
    private lateinit var frequencySpinner: Spinner
    private lateinit var saveBtn: Button
    private lateinit var database:FirebaseDatabase
    private lateinit var dbReference: DatabaseReference
    private lateinit var currentUser: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_info, container, false)

        //Initializing lateinit variables
        age = view.findViewById(R.id.userAge)
        height = view.findViewById(R.id.userHeight)
        weight = view.findViewById(R.id.userWeight)
        gender = view.findViewById(R.id.userGender)
        saveBtn = view.findViewById(R.id.nextPage)
        frequencySpinner = view.findViewById(R.id.activitySpinner)
        dbReference = Firebase.database.reference
        currentUser = Firebase.auth.currentUser!!
        currentUser.let {
            val email = currentUser.email
            userName = email.toString().split("@")[0].toString()
        }

        //
        saveBtn.setOnClickListener {
            validateFields()
        }

        return view
    }

    private fun validateFields() {
        when{
            TextUtils.isEmpty(age.text.toString().trim()) -> {
                age.error = "Please enter your Age"
            }
            TextUtils.isEmpty(height.text.toString().trim()) -> {
                height.error = "Please enter Height"
            }
            TextUtils.isEmpty(weight.text.toString().trim()) -> {
                weight.error = "Please enter Weight"
            }
            TextUtils.isEmpty(gender.text.toString().trim()) -> {
                gender.error = "Please enter your Gender"
            }

            age.text.toString().isNotEmpty() && height.text.toString().isNotEmpty()
                    && weight.text.toString().isNotEmpty() && gender.text.toString().isNotEmpty() ->{
                        saveDetails()
                    }
        }
    }

    private fun saveDetails() {
        val userInfo = UserInformation(age.text.toString(), height.text.toString(), weight.text.toString(), gender.text.toString(), "NA")
        dbReference.child("Users").child(userName).child("Information").setValue(userInfo)
        Toast.makeText(context, "Information saved successfully.", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(requireView()).navigate(R.id.action_infoFragment_to_selectionFragment)
    }
}