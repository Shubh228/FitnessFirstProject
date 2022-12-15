package project.st991558097.shubh

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import project.st991558097.shubh.data.MealItem
import project.st991558097.shubh.data.Reminder
import project.st991558097.shubh.data.WorkoutItem
import project.st991558097.shubh.data.WorkoutRecordItem
import project.st991558097.shubh.diet.dietAdapters.MealListAdapter
import project.st991558097.shubh.workout.workoutAdapters.RecordsListAdapter
import project.st991558097.shubh.workout.workoutAdapters.ReminderListAdapter
import project.st991558097.shubh.workout.workoutAdapters.WorkoutListAdapter

// region RecyclerView
@BindingAdapter("setItems")
fun setItems(recyclerView: RecyclerView, list: List<WorkoutItem>?) {
    (recyclerView.adapter as WorkoutListAdapter).setItems(list)
}
// endregion RecyclerView

@BindingAdapter("setRecords")
fun setRecords(recyclerView: RecyclerView, list: List<WorkoutRecordItem>?) {
    (recyclerView.adapter as RecordsListAdapter).setRecords(list)
}

// region ImageView
@BindingAdapter("loadWithPicasso")
fun loadWithPicasso(imageView: ImageView, imageUrl: String) {
    Picasso.get().load(imageUrl).into(imageView)
}
// endregion ImageView

@BindingAdapter("setRecordList")
fun setRecordList(recyclerView: RecyclerView, list:List<WorkoutRecordItem>?){
    (recyclerView.adapter as RecordsListAdapter).setRecords(list)
}

@BindingAdapter("setReminderList")
fun setReminderList(recyclerView: RecyclerView, list: List<Reminder>?){
    (recyclerView.adapter as ReminderListAdapter).setReminder(list)
}

@BindingAdapter("setMealList")
fun setMealList(recyclerView: RecyclerView, list: List<MealItem>?){
    (recyclerView.adapter as MealListAdapter).setMeal(list)
}