package project.st991558097.shubh.workout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import nl.joery.timerangepicker.TimeRangePicker
import project.st991558097.shubh.R
import project.st991558097.shubh.data.WorkoutRecordItem
import project.st991558097.shubh.databinding.FragmentAddRecordBinding
import project.st991558097.shubh.workout.WorkoutActivity.Companion.editRecord
import java.text.SimpleDateFormat
import java.util.*

class AddRecordFragment : Fragment() {

    private var _binding: FragmentAddRecordBinding? = null
    private val binding get() = _binding!!
    private lateinit var workoutName:String
    private lateinit var workoutImg:String
    private var start: String = ""
    private var end:String = ""
    private var duration:String = ""
    private var feedbackText: String = ""
    private var ratingText: String = ""
    private var dateText:String = ""
    private lateinit var userName:String
    private lateinit var database: DatabaseReference
    private var recordId:String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddRecordBinding.inflate(inflater, container, false)
        workoutName = activity?.intent?.getStringExtra(WorkoutActivity.ARG_NAME).toString()
        workoutImg = activity?.intent?.getStringExtra(WorkoutActivity.ARG_IMG).toString()
        displayItems()

        if (arguments?.getBoolean("edit") == true){
            populateFields()
        }

        val user = Firebase.auth.currentUser!!
        val email = user!!.email
        userName = email?.split("@")?.get(0).toString()
        database = Firebase.database.reference

        binding.iconCalendar.setOnClickListener {
            selectDate()
        }

        binding.workoutDate.setOnClickListener {
            selectDate()
        }

        binding.timePicker.setOnTimeChangeListener(object : TimeRangePicker.OnTimeChangeListener{
            override fun onDurationChange(dur: TimeRangePicker.TimeDuration) {
                Log.d("TimeRangePicker", "Duration: ${dur.durationMinutes}")
                duration = "${dur.durationMinutes} mins"

            }

            override fun onEndTimeChange(endTime: TimeRangePicker.Time) {
                Log.d("TimeRangePicker", "End time: $endTime")
                binding.endTime = endTime.toString()
                end = endTime.toString()
            }

            override fun onStartTimeChange(startTime: TimeRangePicker.Time) {
                Log.d("TimeRangePicker", "Start time: $startTime")
                binding.startTime = startTime.toString()
                start = startTime.toString()
            }

        })

        binding.addRecordButton.setOnClickListener {

            dateText = binding.workoutDate.text.toString()
            ratingText = "${binding.ratingBar.rating} Stars"
            feedbackText = binding.workoutFeedback.text.toString()

            if (arguments?.getBoolean("edit") == true) {
                Log.d("rec", recordId)
                FirebaseDatabase.getInstance().getReference("Users").child(userName).child("Records").child(workoutName).child(recordId).removeValue()
                changeRecord(recordId)
                arguments?.putBoolean("edit", false)
            }else{
                addRecord()
            }
        }

        return binding.root
    }

    private fun changeRecord(recordId: String) {
        val record = WorkoutRecordItem(recordId, workoutName, dateText, start,end, duration, ratingText, feedbackText, workoutImg)
        database.child("Users").child(userName).child("Records").child(workoutName).child(recordId).setValue(record)

        Navigation.findNavController(requireView()).navigate(R.id.action_addRecordFragment_to_recordsListFragment)

    }

    private fun populateFields() {
        binding.date = editRecord.date
        binding.startTime = editRecord.startTime
        binding.endTime = editRecord.endTime
        binding.workoutFeedback.setText(editRecord.feedback)
        //binding.ratingBar.rating = editRecord.rating.toFloat()
        recordId = editRecord.id
    }

    private fun addRecord() {
        val record = WorkoutRecordItem("", workoutName, dateText, start,end, duration, ratingText, feedbackText, workoutImg)
        database.child("Users").child(userName).child("Records").child(workoutName).push().setValue(record)

        Navigation.findNavController(requireView()).navigate(R.id.action_addRecordFragment_to_recordsListFragment)

    }

    private fun selectDate() {

        val datePicker = MaterialDatePicker.Builder.datePicker().build()
        datePicker.show(requireFragmentManager(), "DatePicker")

        datePicker.addOnPositiveButtonClickListener {
            // formatting date in dd/mm/yyyy format.
            val dateFormatter = SimpleDateFormat("dd/MM/yyyy")
            val date = dateFormatter.format(Date(it))
            binding.date = date
            dateText = date
            Toast.makeText(context, "$date is selected", Toast.LENGTH_LONG).show()

        }

        // Setting up the event for when cancelled is clicked
        datePicker.addOnNegativeButtonClickListener {
            Toast.makeText(context, "${datePicker.headerText} is cancelled", Toast.LENGTH_SHORT).show()
        }

        // Setting up the event for when back button is pressed
        datePicker.addOnCancelListener {
            Toast.makeText(context, "Date Picker Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayItems() {
        binding.name = workoutName
        binding.image = workoutImg
    }

  }