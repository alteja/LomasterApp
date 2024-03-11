package com.repair.lomasterapp.errors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.repair.lomasterapp.databinding.ErrorInternetConnectionFragmentBinding


class ErrorInternetConnectionFragment : Fragment() {

    private var _binding: ErrorInternetConnectionFragmentBinding? = null
    private val binding: ErrorInternetConnectionFragmentBinding
        get() = _binding ?: throw RuntimeException("ErrorInternetConnectionFragment == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ErrorInternetConnectionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        buttonsNavigate()

    }

    private fun buttonsNavigate() {

        binding.btnOk.setOnClickListener {

            findNavController().navigate(
                com.repair.lomasterapp.presentation.errors.ErrorInternetConnectionFragmentDirections.actionErrorInternetConnectionFragmentToAutorisationFragment()
            )
        }
    }

}