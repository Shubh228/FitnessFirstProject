package project.st991558097.shubh

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import project.st991558097.shubh.data.WorkoutItem
import project.st991558097.shubh.workout.WorkoutListAdapter

// region RecyclerView
@BindingAdapter("setItems")
fun setItems(recyclerView: RecyclerView, list: List<WorkoutItem>?) {
    (recyclerView.adapter as WorkoutListAdapter).setItems(list)
}
// endregion RecyclerView

// region ImageView
@BindingAdapter("loadWithPicasso")
fun loadWithPicasso(imageView: ImageView, imageUrl: String) {
    Picasso.get().load(imageUrl).into(imageView)
}
// endregion ImageView