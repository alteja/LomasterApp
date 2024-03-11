package com.repair.lomasterapp.authorisation

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.repair.lomasterapp.R
import com.repair.lomasterapp.databinding.SplashScreenFragmentBinding

class SplashScreenFragment: Fragment() {

    private var _binding: SplashScreenFragmentBinding? = null
    private val binding: SplashScreenFragmentBinding
    get() = _binding?: throw RuntimeException("SplashScreenFragment == null")

    private var close_handler: Handler? = null
    private var close_runable: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SplashScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        close_runable = Runnable {
            try {
                lounchAuthorisationFragment()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        close_handler = Handler()
        close_handler?.postDelayed(close_runable!!, 1000)


    }

    private fun lounchAuthorisationFragment() {
        findNavController().navigate(R.id.action_splashScreenFragment_to_autorisationFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}