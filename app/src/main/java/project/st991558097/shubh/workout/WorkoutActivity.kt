package project.st991558097.shubh.workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import project.st991558097.shubh.R

class WorkoutActivity : AppCompatActivity() {

    companion object{
        const val ARG_NAME = "_name"
        const val ARG_IMG = "_img"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        val navController = this.findNavController(R.id.workout_navHost)

        val workoutName = intent.getStringExtra(ARG_NAME) ?: ""

    }
}