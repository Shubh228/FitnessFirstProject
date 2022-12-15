package project.st991558097.shubh.diet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import project.st991558097.shubh.R
import project.st991558097.shubh.data.MealItem
import project.st991558097.shubh.databinding.FragmentAddMealBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class AddMealFragment : Fragment() {


    private var _binding : FragmentAddMealBinding? = null
    private val binding get() = _binding!!
    private var targetCals:String= ""
    private lateinit var database: DatabaseReference
    private lateinit var userName:String
    private var key:String = ""
    private var mealType:String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentAddMealBinding.inflate(inflater, container, false)
        if (arguments?.getBoolean("edit") == true){
            //populateFields()
        }
        val user = Firebase.auth.currentUser!!
        val email = user!!.email
        userName = email?.split("@")?.get(0).toString()
        database = Firebase.database.reference
        targetCals = activity?.intent?.getStringExtra(DietActivity.ARG_TARGET).toString()
        val current = LocalDate.now()
        val formatter = SimpleDateFormat("yyyy/MM/dd")
        val date = Date()
        val today = formatter.format(date)
        key = today.split("/")[0] + today.split("/")[1] + today.split("/")[2]
        Log.d("date", key)

        binding.itemCalories.showSoftInputOnFocus = false
        binding.itemName.showSoftInputOnFocus = false

        binding.firstRow.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.breakfastRadio -> {
                    binding.typeImage.setImageResource(R.drawable.icon_breakfast)
                    binding.heading.text = "Breakfast"
                    mealType = "Breakfast"
                }
                R.id.snackRadio -> {
                    binding.typeImage.setImageResource(R.drawable.icon_snack)
                    binding.heading.text = "Snack"
                    mealType = "Snack"
                }
            }
        }

        binding.secondRow.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.lunchRadio -> {
                    binding.typeImage.setImageResource(R.drawable.icon_lunch)
                    binding.heading.text = "Lunch"
                    mealType = "Lunch"
                }
                R.id.dinnerRadio -> {
                    binding.typeImage.setImageResource(R.drawable.icon_dinner)
                    binding.heading.text = "Dinner"
                    mealType = "Dinner"
                }
            }
        }

        binding.addInfo.setOnClickListener {
            addToDatabase()
            Navigation.findNavController(requireView()).navigate(R.id.action_addMealFragment_to_dietDisplayFragment)
        }
        return binding.root
    }

    /*private fun populateFields() {
        binding
    }
*/
    private fun addToDatabase() {
        val meal = MealItem("",mealType,  binding.itemName.text.toString(), binding.itemCalories.text.toString().toInt())
        database.child("Users").child(userName).child("Diet").child(key).child(mealType).setValue(meal)
    }

}