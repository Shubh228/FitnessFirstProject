package project.st991558097.shubh.workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import project.st991558097.shubh.R

class RecordDetailsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_record_details, container, false)

        view.findViewById<Button>(R.id.goToEditRecord).setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_recordDetailsFragment_to_editRecordFragment)
        }

        return view
    }

}