package project.st991558097.shubh.workout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import project.st991558097.shubh.R
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import project.st991558097.shubh.MainActivity
import project.st991558097.shubh.data.WorkoutRecordItem

class WorkoutActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    companion object{
        const val ARG_NAME = "_name"
        const val ARG_IMG = "_img"
        val editRecord: WorkoutRecordItem = WorkoutRecordItem()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        val drawerLayout = findViewById<DrawerLayout>(R.id.workout_drawer_layout)
        val navView = findViewById<NavigationView>(R.id.workout_nav_view)
        val bottomView = findViewById<BottomNavigationView>(R.id.ff_bottom_navigation)
        val head = findViewById<TextView>(R.id.pageTitle)
        val navController = this.findNavController(R.id.workout_navHost)

        val listener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
            head.text = destination.label
        }

        navController.addOnDestinationChangedListener(listener)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.reminderFragment, R.id.addMealFragment), drawerLayout)
        navView.setupWithNavController(navController)
        bottomView.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.workout_navHost)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //Using the menuitem user can logout from the application
    fun logoutOnClick(item: MenuItem){
        Firebase.auth.signOut()

        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        Toast.makeText(
            applicationContext, "Successfully logged out.",
            Toast.LENGTH_SHORT
        ).show()
    }
}