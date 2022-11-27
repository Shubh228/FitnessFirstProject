package project.st991558097.shubh.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import project.st991558097.shubh.R

class FFHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ff_home)

        val navController = this.findNavController(R.id.homeNavHost)
    }
}