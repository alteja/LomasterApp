package com.repair.lomasterapp.authorisation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.repair.lomasterapp.R
import com.repair.lomasterapp.api.GetTokenSingleton
import com.repair.lomasterapp.databinding.AutorisationFragmentBinding
import com.repair.lomasterapp.entity.TokenObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class AuthorisationFragment : Fragment() {

    private val viewModel: AuthorisationViewModel by viewModels()

    private var _binding: AutorisationFragmentBinding? = null
    private val binding: AutorisationFragmentBinding
        get() = _binding ?: throw RuntimeException("AuthorisationFragment == null")

    private val job = SupervisorJob()

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + coroutineExceptionHandler + job

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    private lateinit var checkingToken: TokenObj

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AutorisationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.authorisationViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        observeViewModel()

        buttonEvents()

        getToken()

        checkPermissions()

    }

    private fun checkPermissions() {

        viewModel.checkForSmsPermission(this.requireActivity())
    }

    private fun buttonEvents() {

        binding.nextAuthor.setOnClickListener {

            viewModel.setUserPhoneLiveData(binding)

            navigateNextFragment()

        }

    }

    private fun getToken() {

        CoroutineScope(coroutineContext).launch {
            checkingToken = GetTokenSingleton.getInstance()
        }

    }

    private fun navigateNextFragment() {

        //если нет интернет-подключения, переходим на экран с ошибкой.
        if (checkApplicationsFunction() == false) {

            findNavController().navigate(R.id.action_autorisationFragment_to_errorInternetConnectionFragment)

        } else {

            //если не удалось получить токен, переходит на экран с ошибкой
            if (checkingToken.tokenError.toString().isNotEmpty()) {

                findNavController().navigate(
                    com.repair.lomasterapp.presentation.authorisation.AuthorisationFragmentDirections.actionAutorisationFragmentToErrorServerConnectionFragment()
                )

            } else {

                CoroutineScope(coroutineContext).launch {
                    launchSmsFragment()
                }

            }

        }
    }


    private suspend fun launchSmsFragment() {

        val checkingResult = viewModel.checkUsersPhone(
            binding.PhoneAuthor1.text.toString(),
            binding.PhoneAuthor2.text.toString()
        )

        CoroutineScope(Dispatchers.Main).launch {

            if (checkingResult == answerCheckUser.ABSENT_IN_DB) {

                findNavController().navigate(
                    com.repair.lomasterapp.presentation.authorisation.AuthorisationFragmentDirections.actionAutorisationFragmentToAskCreateNewUser()
                )

            } else if (checkingResult == answerCheckUser.ATTEND_IN_DB) {

                val resultSending = viewModel.sendSMS()

                if (resultSending.isNotEmpty() && resultSending.containsKey(true)) {

                    findNavController().navigate(
                        com.repair.lomasterapp.presentation.authorisation.AuthorisationFragmentDirections.actionAutorisationFragmentToSendSMSFragment(
                            resultSending.getValue(true)
                        )
                    )
                }
            }
        }


    }

    private fun checkApplicationsFunction(): Boolean {

        return viewModel.checkInternetConnection()

    }


    private fun observeViewModel() {

        viewModel.userPhone.observe(viewLifecycleOwner) {

            binding.PhoneAuthor1.setText(it.phonePart1)
            binding.PhoneAuthor2.setText(it.phonePart2)

        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
        job.cancel()
    }

    companion object {

        private const val ERROR_SERVER_CONNECTION = "ERROR_SERVER_CONNECTION"
        private const val TOKEN = "TOKEN"

    }

}





