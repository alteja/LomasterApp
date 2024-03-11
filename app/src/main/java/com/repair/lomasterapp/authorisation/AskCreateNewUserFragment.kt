package com.repair.lomasterapp.authorisation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.repair.lomasterapp.R
import com.repair.lomasterapp.databinding.AskCreateNewUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AskCreateNewUserFragment : Fragment() {

    private var _binding: AskCreateNewUserBinding? = null
    private val binding: AskCreateNewUserBinding
        get() = _binding ?: throw RuntimeException("AskCreateNewUser == null")


     val viewModel: AuthorisationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AskCreateNewUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.btnCreateUsNo.setOnClickListener {
            backToAuthorisation()
        }

        binding.btnCreateUsOk.setOnClickListener {

            findNavController().navigate(
                com.repair.lomasterapp.presentation.authorisation.AskCreateNewUserFragmentDirections.actionAskCreateNewUserToNewClientInformationFragment()
            )

        }


    }

    private fun backToAuthorisation() {
        findNavController().navigate(R.id.action_askCreateNewUser_to_autorisationFragment)
    }
}