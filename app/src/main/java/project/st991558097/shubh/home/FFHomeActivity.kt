package project.st991558097.shubh.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import project.st991558097.shubh.MainActivity
import project.st991558097.shubh.R

/*
This is homapage activity for our application
 */
class FFHomeActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ff_home)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val navView = findViewById<NavigationView>(R.id.ff_nav_view)
        val bottomView = findViewById<BottomNavigationView>(R.id.ff_bottom_navigation)
        val head = findViewById<TextView>(R.id.pageTitle)
        val navController = this.findNavController(R.id.homeNavHost)

        val listener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
            head.text = destination.label
        }

        navController.addOnDestinationChangedListener(listener)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.reminderFragment), drawerLayout)
        navView.setupWithNavController(navController)
        bottomView.setupWithNavController(navController)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.homeNavHost)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //Using the menu item user can logout from the application
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