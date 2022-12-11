package project.st991558097.shubh.authentication

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import project.st991558097.shubh.R
import project.st991558097.shubh.home.FFHomeActivity

/*
Creator - Shubh Patel(991558097)
SignInFragment user will enter the email address and password will login to FitnessFirst
and if user information is incorrect user will prompt with error message
*/

class SignInFragment : Fragment() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button
    private lateinit var goToSignUp: Button
    private lateinit var auth:FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)


        //Initalizing lateinit variables
        email = view.findViewById(R.id.userEmail)
        password = view.findViewById(R.id.userPassword)
        loginBtn = view.findViewById(R.id.signInButton)
        goToSignUp = view.findViewById(R.id.goToSignUp)
        auth = Firebase.auth

        //On click listener for Login Button
        loginBtn.setOnClickListener {
            validateCreds()
        }

        //On click listener to redirect user to Sign Up page
        goToSignUp.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        return view
    }

    private fun validateCreds() {
        when{
            TextUtils.isEmpty(email.text.toString().trim()) -> {
                email.error = "Please enter Email"
            }
            TextUtils.isEmpty(password.text.toString().trim()) -> {
                password.error = "Please enter Password"
            }

            password.text.toString().isNotEmpty() && email.text.toString().isNotEmpty()->{
                firebaseLogin()
            }
        }
    }

    private fun firebaseLogin() {
        auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener(this.requireActivity()){ task ->
                if (task.isSuccessful){
                    var intent = Intent(this.context, FFHomeActivity::class.java)
                    startActivity(intent)
                }
            }
    }
}