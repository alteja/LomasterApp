package com.repair.lomasterapp.clients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.repair.lomasterapp.R
import com.repair.lomasterapp.databinding.NewClientFragment2Binding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@AndroidEntryPoint
class NewClientContactsFragment : Fragment() {

    @Inject
    lateinit var viewModel : NewClientViewModel

    private var _binding: NewClientFragment2Binding? = null
    private val binding: NewClientFragment2Binding
        get() = _binding ?: throw RuntimeException("NewClientFragment == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NewClientFragment2Binding.inflate(inflater, container, false)
        return binding.root
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newClientViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.btnNext.setOnClickListener {
            CoroutineScope(Dispatchers.Main + coroutineExceptionHandler).launch {
                saveClientOnServer()
            }

        }

        binding.btnNewClientAddPhone.setOnClickListener {
            setVisiblePhones()
        }

        observeViewModel()

    }

    private fun setVisiblePhones() {

        with(binding) {

            if (etPhone2.isVisible) {
                etPhone3.isVisible = true
                btnNewClientAddPhone.isVisible = false
            } else {
                etPhone2.isVisible = true
            }

            if (etPhone3.isVisible) {
                binding.btnNewClientAddPhone.visibility = View.GONE
            }

        }

    }
    private fun observeViewModel() {

        viewModel.clientData.observe(viewLifecycleOwner) {
            binding.apply {
                etPhone1.setText(it.phone.get(0))
                if (it.phone.size > 1) {
                    etPhone2.setText(it.phone[1])
                }
                if (it.phone.size > 2) {
                    etPhone3.setText(it.phone[2])
                }
                etAddress.setText(it.address.toString())
                etEmail.setText(it.email.toString())
            }
        }
    }

    suspend fun saveClientOnServer() {

        with(binding.btnNext){
            isClickable = false
            isEnabled = false
            background = context?.getDrawable(R.drawable.button_grey)
        }

        val resultSaving = CoroutineScope(coroutineContext).async {
            return@async viewModel.saveClientOnServer()
        }.await()

        println("SAVING RESULT$resultSaving")

        with(binding.btnNext){
            isClickable = true
            isEnabled = true
            background = context?.getDrawable(R.drawable.button_green)
        }

        if (resultSaving) {
            goToMainScreen()
        }

    }

    private fun goToMainScreen() {

        if (viewModel.checkClientContacts()) {

            var userPhoneArgs = viewModel.getClientPhoneFragment2(binding)

            findNavController().navigate(
                com.repair.lomasterapp.presentation.clients.NewClientContactsFragmentDirections.actionNewClientContactsFragmentToMainFragment()
            )
        }
    }

}