package project.st991558097.shubh.diet

import android.app.AlertDialog
import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import project.st991558097.shubh.R
import project.st991558097.shubh.databinding.FragmentDietDisplayBinding
import project.st991558097.shubh.diet.dietAdapters.MealListAdapter
import project.st991558097.shubh.viewModel.DietViewModel

class DietDisplayFragment : Fragment() {

    private var _binding: FragmentDietDisplayBinding? = null
    private val binding get() = _binding!!

    private val dietViewModel:DietViewModel by lazy {
        ViewModelProvider(requireActivity())[DietViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDietDisplayBinding.inflate(inflater, container, false)

        binding.noListText.visibility = View.GONE
        val mealListAdapter = MealListAdapter()
        binding.dietListRV.adapter = mealListAdapter
        binding.dietListRV.layoutManager = LinearLayoutManager(this.context)

        binding.goToAddMeal.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_dietDisplayFragment_to_addMealFragment)
        }
        val touch = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.RIGHT -> {
                        mealListAdapter.deleteItem(viewHolder.adapterPosition)
                        /*val builder = AlertDialog.Builder(context)
                        builder.setMessage("Are you sure you want to Delete?")
                            .setCancelable(false)
                            .setPositiveButton("Yes") { dialog, id ->
                            }
                            .setNegativeButton("No") { dialog, id ->
                                dialog.dismiss()
                            }
                        val alert = builder.create()
                        alert.show()*/
                    }

                    ItemTouchHelper.LEFT -> {
                        mealListAdapter.setItem(viewHolder.adapterPosition)
                        val bundle = Bundle()
                        bundle.putBoolean("edit", true)
                        Navigation.findNavController(requireView()).navigate(R.id.action_dietDisplayFragment_to_addMealFragment, bundle)
                    }
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(context!!, R.color.yellow))
                    .addSwipeLeftActionIcon(R.drawable.icon_edit)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(context!!, R.color.red))
                    .addSwipeRightActionIcon(R.drawable.icon_trash)
                    .create()
                    .decorate()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        })

        touch.attachToRecyclerView(binding.dietListRV)

        dietViewModel.fetchMealList()
        dietViewModel.dietLiveData.observe((this.viewLifecycleOwner)){
            it -> mealListAdapter.setMeal(it)
            if (it.isEmpty()){
                binding.dietListRV.visibility = View.GONE
                binding.noListText.visibility = View.VISIBLE
            }else{
                binding.dietListRV.visibility = View.VISIBLE
                binding.noListText.visibility = View.GONE
            }

        }


        return binding.root
    }

}