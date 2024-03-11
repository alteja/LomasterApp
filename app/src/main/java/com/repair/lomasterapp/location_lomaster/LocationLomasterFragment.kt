package com.repair.lomasterapp.location_lomaster

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.repair.lomasterapp.databinding.LocationLomasterFragmentBinding


class LocationLomasterFragment: Fragment(){

    private var _binding: LocationLomasterFragmentBinding? = null
    private val binding: LocationLomasterFragmentBinding
        get() = _binding ?: throw RuntimeException("LocationLomasterFragmentBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LocationLomasterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        setViews()

    }

    private fun setViews() {

        // binding.tvLinkYoutube.movementMethod = LinkMovementMethod.getInstance();
        binding.tvLinkYoutube.setOnClickListener {
            openLinkYoutube()
        }

        binding.tvLinkGMap.setOnClickListener {
            openLinkGoogleMap()
        }

        binding.btnBackLocation.setOnClickListener {
            findNavController().navigate(com.repair.lomasterapp.presentation.location_lomaster.LocationLomasterFragmentDirections.actionLocationLomasterFragmentToMainFragment())
        }
    }


    fun openLinkYoutube() {

        val address: Uri = Uri.parse(binding.tvLinkYoutube.text.toString())
        val openlink = Intent(Intent.ACTION_VIEW, address)

        if (openlink.resolveActivity(requireActivity().packageManager)!= null) {
            startActivity(openlink)
        }

    }

    fun openLinkGoogleMap() {

        val geoUriString = "geo:49.43996801754227,32.06553287957417?z=20"

        val geoUri: Uri = Uri.parse(geoUriString)

        val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)

        try {
            startActivity(mapIntent)
        } catch (e: Exception) {
            println(e.message)
        }

    }





}


