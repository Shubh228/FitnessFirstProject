package project.st991558097.shubh.authentication

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import project.st991558097.shubh.R
import project.st991558097.shubh.data.User
import project.st991558097.shubh.onboarding.OnboardingActivity

/*
Creator - Shubh Patel(991558097)
In SignUpFragment user can register him/her and can use our application
 */
class SignUpFragment : Fragment() {

    //private late-init variables
    private lateinit var name:TextInputEditText
    private lateinit var email:TextInputEditText
    private lateinit var password:TextInputEditText
    private lateinit var confirmPass:TextInputEditText
    private lateinit var userName:String
    private lateinit var signUpBtn:Button
    private lateinit var goToSignIn:Button
    private lateinit var auth:FirebaseAuth
    private lateinit var dataRef:DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        //Initalizing lateinit variables
        name = view.findViewById(R.id.userName)
        email = view.findViewById(R.id.userEmail)
        password = view.findViewById(R.id.userPassword)
        confirmPass = view.findViewById(R.id.userConfirmPassword)
        signUpBtn = view.findViewById(R.id.signUpButton)
        goToSignIn = view.findViewById(R.id.goToLogin)
        dataRef = Firebase.database.reference
        auth = Firebase.auth

        signUpBtn.setOnClickListener {
            userName = email.text.toString().split("@")?.get(0).toString()
            validateCreds()
        }

        goToSignIn.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_signUpFragment_to_signInFragment)
        }
        return view
    }

    //validating the user credentials while user sign up for the account
    private fun validateCreds() {
        when{
            TextUtils.isEmpty(name.text.toString().trim()) -> {
                name.error = "Please enter Name"
            }
            TextUtils.isEmpty(email.text.toString().trim()) -> {
                email.error = "Please enter Email"
            }
            TextUtils.isEmpty(password.text.toString().trim()) -> {
                password.error = "Please enter Password"
            }
            TextUtils.isEmpty(confirmPass.text.toString().trim()) -> {
                confirmPass.error = "Please enter Password again"
            }

            name.text.toString().isNotEmpty() && password.text.toString().isNotEmpty() &&
                    email.text.toString().isNotEmpty() && confirmPass.text.toString().isNotEmpty() ->{
                        if (email.text.toString().matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))){
                            if (password.text.toString().length>=8){
                                if (password.text.toString() != confirmPass.text.toString()){
                                    confirmPass.error = "Passwords do not match. Please try again."
                                }
                                else{
                                    signUp()
                                }
                            }else{
                                password.error = "Password length should be greater than 8."
                            }
                        }
                    }
        }
    }

    //Using the firebase authentication we are adding the user email id and password into our firebase database
    private fun signUp() {
        auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener {
                task ->
                if (task.isSuccessful){
                    val user = User(userName, name.text.toString(), email.text.toString(), password.text.toString())
                    dataRef.child("Users").child(userName).setValue(user)
                    Toast.makeText(context, "User Registered Successfully.", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this.context, OnboardingActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}