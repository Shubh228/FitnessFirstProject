package project.st991558097.shubh.workout

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import project.st991558097.shubh.R
import project.st991558097.shubh.databinding.FragmentPlanWorkoutBinding
import project.st991558097.shubh.workout.notifications.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min


class PlanWorkoutFragment : Fragment() {

    private var _binding: FragmentPlanWorkoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var workoutName:String
    private lateinit var workoutImg:String
    private var minute:Int = 0
    private var hour:Int = 0
    private var date:Int = 0
    private var month:Int = 0
    private var year:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPlanWorkoutBinding.inflate(inflater, container, false)
        workoutName = activity?.intent?.getStringExtra(WorkoutActivity.ARG_NAME).toString()
        workoutImg = activity?.intent?.getStringExtra(WorkoutActivity.ARG_IMG).toString()

        binding.name = workoutName
        binding.image = workoutImg

        createNotificationChannel()

        binding.timePicker.setOnClickListener {
            selectTime()
        }

        binding.datePicker.setOnClickListener {
            selectDate()
        }

        binding.setReminder.setOnClickListener {
            scheduleNotification()
        }


        // Inflate the layout for this fragment
        return binding.root
    }

    private fun selectDate() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select reminder date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        datePicker.show(requireFragmentManager(), "datePicker")

        datePicker.addOnPositiveButtonClickListener {
            val dateFormatter = SimpleDateFormat("dd/MM/yyyy")
            val selectedDate = dateFormatter.format(Date(it))
            date = Integer.parseInt(selectedDate.split("/")[0])
            month = Integer.parseInt(selectedDate.split("/")[1])
            year = Integer.parseInt(selectedDate.split("/")[2])
            Log.d("date", date.toString())
            Log.d("month", month.toString())
            Log.d("year", year.toString())
        }
    }

    private fun selectTime() {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(10)
            .setTitleText("Select Reminder time")
            .build()

        MaterialTimePicker.Builder().setInputMode(INPUT_MODE_CLOCK)

        picker.show(requireFragmentManager(), "timePicker");

        picker.addOnPositiveButtonClickListener {
            minute = picker.minute
            hour = picker.hour
        }
    }

    private fun createNotificationChannel() {
        Log.d("Inside notif channel", "true")

        val name = "Notif Channel"
        val desc = "A description of the channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance)
        channel.description = desc
        val notificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun scheduleNotification() {
        Log.d("Inside schedule", "true")
        val intent = Intent(context, Notification::class.java)
        val title = "Time for $workoutName"
        val message = "It's almost time to start working out."
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent  = PendingIntent.getBroadcast(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val time = getTime()

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )

        showAlert(time, title, message)

    }

    private fun showAlert(time: Long, title: String, message: String) {
        Log.d("Inside alert", "true")

        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(requireContext())
        val timeFormat = android.text.format.DateFormat.getTimeFormat(requireContext())

        AlertDialog.Builder(requireContext())
            .setTitle("Reminder Scheduled")
            .setMessage("$workoutName starts at\nDate: ${dateFormat.format(date)} \nTime: ${timeFormat.format(date)}")
            .setPositiveButton("Okay"){_,_ ->
                //Navigation.findNavController(requireView()).navigate(R.id.action_planWorkoutFragment_to_workoutDetailsFragment)
            }
            .setNegativeButton("Cancel"){_,_ ->
                Navigation.findNavController(requireView()).navigate(R.id.action_planWorkoutFragment_to_workoutDetailsFragment)
            }
    }

    private fun getTime(): Long {
        Log.d("Inside getTime", "true")

        val calendar = Calendar.getInstance()
        calendar.set(year,month, date, hour, minute)

        return calendar.timeInMillis
    }
}