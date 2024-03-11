package com.repair.lomasterapp.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.repair.lomasterapp.LomasterApplication.Companion.appContext
import com.repair.lomasterapp.R
import com.repair.lomasterapp.databinding.NewOrderFragmentBinding
import com.repair.lomasterapp.entity.UserPhone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class NewOrderFragment : Fragment() {

    private val viewModel: NewOrderViewModel by viewModels()

    private var _binding: NewOrderFragmentBinding? = null
    private val binding: NewOrderFragmentBinding
        get() = _binding ?: throw RuntimeException("RequestFragment == null")

    @Inject
    lateinit var userPhone: UserPhone


    private val jobSetEditText = SupervisorJob()

    private val jobSaveOrder = SupervisorJob()

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + coroutineExceptionHandler

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    private var wasSaving = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = NewOrderFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.newOrderViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setAdapters()
        setItemSelectedIsNewDevice()

        observeViewModel()

        if (viewModel.orderData.value == null) {

            setClientInformation()

        }

        setClickListeners()

    }

    private fun setAdapters() {

        setTypeDeviceNewOrderAdapter()
        setBrandDeviceNewOrderAdapter()
        setDefectsDeviceNewOrderAdapter()
        setEquipmentDeviceNewOrderAdapter()

        setIsNewDeviceAdapter()

    }

    private fun setClickListeners() {

        with(binding) {
            saveNewOrder.setOnClickListener {
                CoroutineScope(coroutineContext + jobSaveOrder).launch {
                    saveNewOrder()
                }
            }

            etMasterCallTime.setOnClickListener {
                findNavController().navigate(
                    com.repair.lomasterapp.presentation.requests.NewOrderFragmentDirections.actionNewRequestFragmentToPickerDate()
                )
            }
        }

    }

    private suspend fun saveNewOrder() {

        if (viewModel.CheckNewOrder()) {

            with(binding.saveNewOrder) {
                isEnabled = false
                isClickable = false
                background = context?.getDrawable(R.drawable.button_grey)
            }

            val savingResult = viewModel.saveNewOrder()

            println("savingResult fragment $savingResult")

            if (savingResult) {
                goToMainScreen()
            } else {
                Toast.makeText(
                    context,
                    context?.getString(R.string.error_saving_order),
                    Toast.LENGTH_LONG
                ).show()
            }

            with(binding.saveNewOrder) {
                isEnabled = true
                background = appContext?.getDrawable(R.drawable.button_green)
                isClickable = true
            }

        }

    }

    private fun goToMainScreen() {
        findNavController().navigate(
            com.repair.lomasterapp.presentation.requests.NewOrderFragmentDirections.actionNewRequestFragmentToMainFragment(
            )
        )
    }

    private fun setClientInformation() {

        CoroutineScope(coroutineContext).launch {
            viewModel.setClientInformation()
        }

    }


    fun observeViewModel() {

        with(viewModel) {

            orderData.observe(viewLifecycleOwner) {

                binding.apply {

                    tvFioInfoClNewReq.text = it.fioClient.toString()

                    tvNumberPhoneInfoClNewReq.text = it.phoneClient.toString()

                    tvEmailInfoClNewReq.text = it.emailClient.toString()
                }

            }

            with(binding) {

                typeDevice.observe(viewLifecycleOwner) {
                    etTypeDevice.setText(it)

                }

                serialNumberDevice.observe(viewLifecycleOwner) {
                    etSerialNumberDevice.setText(it)
                }

                brandDevice.observe(viewLifecycleOwner) {
                    etBrandDevice.setText(it)
                }

                modelDevice.observe(viewLifecycleOwner) {
                    etModelDevice.setText(it)
                }

                passwordDevice.observe(viewLifecycleOwner) {
                    etPasswordDevice.setText(it)
                }

                defectDevice.observe(viewLifecycleOwner) {
                    etDefectDevice.setText(it)
                }

                equipmentDevice.observe(viewLifecycleOwner) {
                    etEquipmentDevice.setText(it)
                }

                isQuiqly.observe(viewLifecycleOwner) {
                    checkboxIsQuiqly.isChecked = it
                }

                masterCallDate.observe(viewLifecycleOwner) {
                    etMasterCallTime.setText(it)
                }

            }

        }


    }

    fun setTypeDeviceNewOrderAdapter(
    ) {

        CoroutineScope(coroutineContext + jobSetEditText).launch {
            binding.etTypeDevice.setAdapter(

                ArrayAdapter(
                    appContext,
                    R.layout.support_simple_spinner_dropdown_item,
                    viewModel.loadTypeDevice()
                )
            )
        }


    }

//    fun setModelDeviceNewReq(
//    ) {
//        binding.modelDeviceReq.setAdapter(
//            ArrayAdapter(
//                appContext,
//                R.layout.simple_dropdown_item_1line,
//                viewModel.
//            )
//        )
//
//    }

    fun setBrandDeviceNewOrderAdapter() {

        CoroutineScope(coroutineContext + jobSetEditText).launch {

            binding.etBrandDevice.setAdapter(
                ArrayAdapter(
                    appContext,
                    R.layout.support_simple_spinner_dropdown_item,
                    viewModel.loadBrandDevice()
                )
            )
        }

    }

    fun setDefectsDeviceNewOrderAdapter() {

        CoroutineScope(coroutineContext + jobSetEditText).launch {
            binding.etDefectDevice.setAdapter(
                ArrayAdapter(
                    appContext,
                    R.layout.support_simple_spinner_dropdown_item,
                    viewModel.loadDefectDevice()
                )
            )
        }
    }

    fun setEquipmentDeviceNewOrderAdapter() {

        CoroutineScope(coroutineContext + jobSetEditText).launch {
            binding.etEquipmentDevice.setAdapter(
                ArrayAdapter(
                    appContext,
                    R.layout.support_simple_spinner_dropdown_item,
                    viewModel.loaEdequipmentDevice()
                )
            )
        }
    }

    fun setIsNewDeviceAdapter(){

        binding.spinnerIsNewDevice.adapter = context?.let {
            ArrayAdapter(
                it,
                R.layout.support_simple_spinner_dropdown_item,
                viewModel.IsNewDeviceArray()
            )
        }

        viewModel.setIsNewDeviceToLiveData(0)

    }
    private fun setItemSelectedIsNewDevice() {

        binding.spinnerIsNewDevice.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.setIsNewDeviceToLiveData(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

        viewModel.setIsNewDeviceToLiveData(0)

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        jobSaveOrder.cancel()
        jobSetEditText.cancel()
    }
}



