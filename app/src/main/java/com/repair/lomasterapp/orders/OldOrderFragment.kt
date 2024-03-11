package com.repair.lomasterapp.orders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.repair.lomasterapp.LomasterApplication.Companion.appContext
import com.repair.lomasterapp.R
import com.repair.lomasterapp.calendarDate.TransformDatesRepository.dateToString
import com.repair.lomasterapp.databinding.OldOrderFragmentBinding
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
class OldOrderFragment : Fragment() {

    private val args by navArgs<com.repair.lomasterapp.presentation.requests.OldOrderFragmentArgs>()
    @Inject
    lateinit var userPhone: UserPhone

    private val orderId by lazy {

        try {
            args.oldOrderId
        } catch (e: Exception) {
        }

    }

    private lateinit var settingsListAdapter: SettingServicesAdapter


    private val viewModel: OldOrderViewModel by viewModels()

    private var _binding: OldOrderFragmentBinding? = null
    private val binding: OldOrderFragmentBinding
        get() = _binding ?: throw RuntimeException("OldOrderFragment == null")

    private val job = SupervisorJob()

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + coroutineExceptionHandler + job

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = OldOrderFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        launchGettingData()

    }

    private fun launchGettingData() {

        //перед формированием макета, подгрузим данные полей заказа с сервера.
        CoroutineScope(coroutineContext).launch {

            val oldOrder = viewModel.getOrderDataFromServer(
                orderId as Int,
                userPhone.numberPhone
            )

            viewModel.setOrderDBToLiveData(oldOrder)

            setupRecyclerView()

            observeViewModel()

            setViews()

        }

    }

    private fun setViews() {

        binding.buttomOk.setOnClickListener {
            findNavController().navigate(
                com.repair.lomasterapp.presentation.requests.OldOrderFragmentDirections.actionOldOrderFragmentToListOrdersFragment()
            )
        }

        binding.buttonPayReq.setOnClickListener {

        }

        val rowDown = appContext.getDrawable(R.drawable.row_down)
        val rowUp = appContext.getDrawable(R.drawable.row_up)


        binding.imAboutSettings.setOnClickListener{

            if (binding.lnAboutDevice.visibility == View.GONE){
                binding.lnAboutDevice.visibility = View.VISIBLE
                binding.imAboutSettings.setImageDrawable(rowDown)
            }else{
                binding.lnAboutDevice.visibility = View.GONE
                binding.imAboutSettings.setImageDrawable(rowUp)
            }

        }

        binding.imInfoClient.setOnClickListener {
            if (binding.lnInfoClient.visibility == View.GONE){
                binding.lnInfoClient.visibility = View.VISIBLE
                binding.imInfoClient.setImageDrawable(rowDown)
            } else{
                binding.lnInfoClient.visibility = View.GONE
                binding.imInfoClient.setImageDrawable(rowUp)
            }
        }
        
        binding.imSettings.setOnClickListener {

            if (binding.rvSettingsServices.visibility == View.GONE){
                binding.rvSettingsServices.visibility = View.VISIBLE
                binding.imSettings.setImageDrawable(rowDown)
            }else{
                binding.rvSettingsServices.visibility = View.GONE
                binding.imSettings.setImageDrawable(rowUp)
            }
        }

    }

    private fun setupRecyclerView() {

        with(binding.rvSettingsServices) {

            settingsListAdapter = SettingServicesAdapter()
            adapter = settingsListAdapter

        }

    }

    private fun observeViewModel() {

        viewModel.orderData.observe(viewLifecycleOwner) {

            binding.apply {

                tvFioInfoClNewReq.text = it.fioClient.toString()
                tvNumberPhoneInfoClNewReq.text = it.phoneClient.toString()
                tvEmailInfoClNewReq.text = it.emailClient.toString()
                typeDeviceOldReq.setText(it.kindof_good.toString())
                serialNumberDeviceOldReq.setText(it.serial.toString())
                brandDeviceOldReq.setText(it.brand.toString())
                modelDeviceOldReq.setText(it.model.toString())
                passwordDeviceOldReq.setText(it.passwordDevice ?: "")
                defectDeviceOldReq.setText(it.malfunction.toString())

                if (it.appearance == null){
                    isNewDeviceOldReq.setText("")
                }else{
                    isNewDeviceOldReq.setText(it.appearance.toString())
                }

                equipmentDeviceOldReq.setText(it.packagelist.toString())
                tvStatus.text = it.status?.name.toString()
                dateOder.text = dateToString(it.dateSetting)

                orderSumm.text = viewModel!!.getGeneralPrice().toString().plus(" грн.")
            }

        }

        viewModel.settingsServicesData.observe(viewLifecycleOwner){
            settingsListAdapter.setSettingsList(it)
            settingsListAdapter.submitList(it)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
        job.cancel()
    }

}