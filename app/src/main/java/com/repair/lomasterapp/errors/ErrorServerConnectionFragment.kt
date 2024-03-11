package com.repair.lomasterapp.errors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.repair.lomasterapp.databinding.ErrorServerConnectionFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ErrorServerConnectionFragment : Fragment() {

    private var _binding: ErrorServerConnectionFragmentBinding? = null
    private val binding: ErrorServerConnectionFragmentBinding
        get() = _binding ?: throw RuntimeException("ErrorServerConnectionFragment == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ErrorServerConnectionFragmentBinding.inflate(inflater, container, false)
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
                com.repair.lomasterapp.presentation.errors.ErrorServerConnectionFragmentDirections.actionErrorServerConnectionFragmentToMainFragment()
            )
        }
    }

}