package com.repair.lomasterapp.clients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.repair.lomasterapp.R
import com.repair.lomasterapp.clients.ClientRepositoryDB.getClientInformationFromServer
import com.repair.lomasterapp.databinding.ClientFragmentBinding
import com.repair.lomasterapp.entity.ClientObj
import com.repair.lomasterapp.entity.UserPhone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class ClientFragment : Fragment() {

    @Inject
    lateinit var userPhone: UserPhone

    private val viewModel: ClientViewModel by viewModels()

    private var _binding: ClientFragmentBinding? = null
    private val binding: ClientFragmentBinding
        get() = _binding ?: throw RuntimeException("ClientFragment == null")

    private val job = SupervisorJob()

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + coroutineExceptionHandler + job

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    private var oldClientObject: ClientObj? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ClientFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clientViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        launchClient()

        setButtons()

    }

    private fun launchClient() {

        try {

            CoroutineScope(Dispatchers.Main + coroutineExceptionHandler).launch {

                oldClientObject = getClientFromServer()

                viewModel.seClientDataViewModel(oldClientObject)

                observeViewModel()

            }


        } catch (e: NullPointerException) {
            var mess = e.message
        }


    }

    private fun setButtons() {

        binding.btnNext.setOnClickListener {

            CoroutineScope(Dispatchers.Main + coroutineExceptionHandler).launch {
                saveClientOnServer()
            }
        }

        binding.btnBackClient.setOnClickListener {
            findNavController().navigate(
                com.repair.lomasterapp.presentation.clients.ClientFragmentDirections.actionClientFragmentToMainFragment()
            )
        }

        binding.btnClientAddPhone.setOnClickListener {
            viewModel.setVisiblePhones(binding)
        }
    }

    private fun goToMainFragment() {
        findNavController().navigate(
            com.repair.lomasterapp.presentation.clients.ClientFragmentDirections.actionClientFragmentToMainFragment()
        )
    }

    suspend fun getClientFromServer() =

        CoroutineScope(coroutineContext).async {
            return@async getClientInformationFromServer(
                userPhone.numberPhone
            )
        }.await()

    suspend fun saveClientOnServer() {

        with(binding.btnNext) {
            isClickable = false
            isEnabled = false
            background = context?.getDrawable(R.drawable.button_grey)
        }

        if (viewModel.saveClientOnServer()) {

            println("go to next screen")
            goToMainFragment()
        }

        with(binding.btnNext) {
            isClickable = true
            isEnabled = true
            background = context?.getDrawable(R.drawable.button_green)
        }

        //  }
    }


    fun observeViewModel() {

        viewModel.clientData.observe(viewLifecycleOwner) {
            binding.apply {

                etNameClient.setText(viewModel.parseNameClient(it.name))
                etFirstNameClient.setText(viewModel.parseFirstNameClient(it.firstName))
                etSernameClient.setText(viewModel.parseSecondNameClient(it.secondName))

                etPhone1Client.setText(it.phone.get(0))

                if (it.phone.size > 1) {
                    etPhone2Client.setText(it.phone[1])
                }

                if (it.phone.size > 2) {
                    etPhone3Client.setText(it.phone[2])
                }
                etAddressClient.setText(it.address.toString())
                etEmailClient.setText(it.email.toString())
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        job.cancel()
    }
}