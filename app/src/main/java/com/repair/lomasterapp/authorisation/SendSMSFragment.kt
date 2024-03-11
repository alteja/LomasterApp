package com.repair.lomasterapp.authorisation
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.repair.lomasterapp.R
import com.repair.lomasterapp.databinding.SendMessageFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SendSMSFragment : Fragment() {

    private var _binding: SendMessageFragmentBinding? = null
    private val binding: SendMessageFragmentBinding
        get() = _binding ?: throw RuntimeException("SendSMSFragment == null")


    private val viewModel: SendSMSViewModel by viewModels()

    private val args by navArgs<com.repair.lomasterapp.presentation.authorisation.SendSMSFragmentArgs>()

    private val smsKod by lazy {

        try {
            args.smsKod
        } catch (e: Exception) {
            null
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SendMessageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sendSMSViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        observeViewModel()

        buttonEvents()

    }

    private fun buttonEvents() {

        binding.btnSmsLogin.setOnClickListener {


            if (binding.etKodSms.text.isNotEmpty()) {

                var userKod: Int

                try {
                    userKod = binding.etKodSms.text.toString().toInt()
                }catch (e:java.lang.Exception){
                    userKod = 0
                }


                if (smsKod?.let {
                        viewModel.checkingSmsKod(
                            it,
                            userKod
                        )
                    } == true) {

                    findNavController().navigate(
                        com.repair.lomasterapp.presentation.authorisation.SendSMSFragmentDirections.actionSendSMSFragmentToMainFragment()
                    )

                } else {

                    Toast.makeText(
                        context,
                        context?.getString(R.string.error_kod_sms),
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(
                    context,
                    context?.getString(R.string.empty_error_kod_sms),
                    Toast.LENGTH_LONG
                ).show()
            }

        }

        binding.btnSmsBack.setOnClickListener {
            findNavController().navigate(R.id.action_sendSMSFragment_to_autorisationFragment)
        }


    }

    private fun observeViewModel() {
       // viewModel.userKod.observe(viewLifecycleOwner){
       //     binding.etKodSms.setText(it.toString())
       // }
    }

}