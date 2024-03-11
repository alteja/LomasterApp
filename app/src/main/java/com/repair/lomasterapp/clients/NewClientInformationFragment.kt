package com.repair.lomasterapp.clients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.repair.lomasterapp.databinding.NewClientFragment1Binding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NewClientInformationFragment : Fragment() {

    private val viewModel: NewClientViewModel by viewModels()

    private var _binding: NewClientFragment1Binding? = null
    private val binding: NewClientFragment1Binding
        get() = _binding ?: throw RuntimeException("AuthorisationFragment == null")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NewClientFragment1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newClientViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        observeViewModel()

        viewModel.setClientInformationToLiveData()

        binding.btnNext.setOnClickListener {

            goToContactsNewClient()

        }

    }

    private fun observeViewModel() {

        viewModel.clientData.observe(viewLifecycleOwner) {
            binding.apply {
                etFirstName.setText(it.firstName.toString())
                etName.setText(it.name.toString())
                etSername.setText(it.secondName.toString())
            }
        }
    }


    private fun goToContactsNewClient() {

        var savingResult: Boolean

        CoroutineScope(Dispatchers.Main).launch {
            savingResult = viewModel.checkClientInformation()

            if (savingResult){
                findNavController().navigate(
                    com.repair.lomasterapp.presentation.clients.NewClientInformationFragmentDirections.actionNewClientInformationFragmentToNewClientContactsFragment()
                )
            }
        }
    }


}


