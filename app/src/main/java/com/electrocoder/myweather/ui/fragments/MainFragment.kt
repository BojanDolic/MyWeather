package com.electrocoder.myweather.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.*
import androidx.navigation.fragment.findNavController
import com.electrocoder.myweather.R
import com.electrocoder.myweather.databinding.MainFragmentBinding
import com.electrocoder.myweather.ui.viewmodels.MainViewModel

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)

        ViewCompat.setOnApplyWindowInsetsListener(binding.citySearchContainer) { _view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Apply the insets as a margin to the view. Here the system is setting
            // only the bottom, left, and right dimensions, but apply whichever insets are
            // appropriate to your layout. You can also update the view padding
            // if that's more appropriate.
            _view.updateLayoutParams<ViewGroup.MarginLayoutParams>() {
                topMargin = insets.top
                Log.d("TAG", "onViewCreated: POZVAN INSET")
            }

            /*_view.updatePadding(
                bottom = insets.bottom
            )*/

            // Return CONSUMED if you don't want want the window insets to keep being
            // passed down to descendant views.
            WindowInsetsCompat.CONSUMED
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}