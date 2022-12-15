package project.st991558097.shubh.diet.dietAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import project.st991558097.shubh.R
import project.st991558097.shubh.data.MealItem
import project.st991558097.shubh.data.Reminder
import project.st991558097.shubh.databinding.DietDisplayRowBinding
import project.st991558097.shubh.databinding.ReminderRowBinding
import project.st991558097.shubh.diet.DietActivity
import project.st991558097.shubh.workout.WorkoutActivity

class MealListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val dietItems = mutableListOf<MealItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MealItemViewHolder(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MealItemViewHolder).onBind(dietItems[position])
    }

    override fun getItemCount(): Int {
        return dietItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteItem(pos: Int) {
        val email = Firebase.auth.currentUser?.email
        val username = email?.split("@")?.get(0).toString()
        FirebaseDatabase.getInstance().getReference("Users").child(username).child("Diet").child(dietItems[pos].id).child(dietItems[pos].mealType).removeValue()

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMeal(dietItems: List<MealItem>?) {
        this.dietItems.clear()
        this.dietItems.addAll(dietItems ?: emptyList())
        notifyDataSetChanged()
    }

    fun setItem(pos: Int){
        var item = dietItems[pos]

        DietActivity.editMeal.id = item.id
        DietActivity.editMeal.mealType = item.mealType
        DietActivity.editMeal.mealName = item.mealName
        DietActivity.editMeal.calories = item.calories

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMealList(dietItems: List<MealItem>?) {
        this.dietItems.clear()
        this.dietItems.addAll(dietItems ?: emptyList())
        notifyDataSetChanged()
    }

    inner class MealItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.diet_display_row, parent, false)
    ) {

        private val binding = DietDisplayRowBinding.bind(itemView)

        fun onBind(meal: MealItem) {
            binding.itemName = meal.mealName
            binding.cals = meal.calories.toString()
            binding.type = meal.mealType
        }
    }
}