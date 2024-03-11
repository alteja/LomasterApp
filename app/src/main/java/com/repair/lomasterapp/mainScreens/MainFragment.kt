package com.repair.lomasterapp.mainScreens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.repair.lomasterapp.R
import com.repair.lomasterapp.databinding.MainFragmentBinding
import com.repair.lomasterapp.entity.ClientObj
import com.repair.lomasterapp.entity.UserPhone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject
    lateinit var userPhone: UserPhone

    @Inject
    lateinit var clientObj: ClientObj

    private val userName by lazy {

        try {
            clientObj.name
        } catch (e: Exception) {
            ""
        }

    }

    private var token = ""


    private val viewModel: MainFragmentViewModel by viewModels()

    private var _binding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding
        get() = _binding ?: throw RuntimeException("MainFragment == null")

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
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainFragmentViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        launchData()

        buttonsNavigate()
        // addTextChangeListeners()
        // observeViewModel()

        setSwitcherAdvertising()

    }

    private fun launchData() {

        if (userName.isNullOrEmpty()) {

            CoroutineScope(coroutineContext).launch {

                val nameClient = viewModel.getClientInformationDTO(userPhone.numberPhone)
                binding.tvNameMainScreen.text =
                    getString(R.string.tv_hello_user).plus(nameClient).plus("!")

            }

        }
    }

    private fun buttonsNavigate() {

        binding.btnMainNewRequest.setOnClickListener {

            findNavController().navigate(
                com.repair.lomasterapp.presentation.mainScreens.MainFragmentDirections.actionMainFragmentToNewRequestFragment()
            )

        }

        binding.btnOpenClientInfo.setOnClickListener {

            findNavController().navigate(
                com.repair.lomasterapp.presentation.mainScreens.MainFragmentDirections.actionMainFragmentToClientFragment()
            )

        }

        binding.btnMainRequests.setOnClickListener {

            findNavController().navigate(
                com.repair.lomasterapp.presentation.mainScreens.MainFragmentDirections.actionMainFragmentToListOrdersFragment()
            )

        }

        binding.btnMainCallLomaster.setOnClickListener {

            findNavController().navigate(
                com.repair.lomasterapp.presentation.mainScreens.MainFragmentDirections.actionMainFragmentToCompanyContactFragment()
            )

        }

        binding.btnMainSearchLomaster.setOnClickListener {

            findNavController().navigate(
                com.repair.lomasterapp.presentation.mainScreens.MainFragmentDirections.actionMainFragmentToLocationLomasterFragment()
            )

        }

    }

    private fun setSwitcherAdvertising(){

        val listImgAdvertising = ArrayList<Int>()

        with(listImgAdvertising){
            add(R.drawable.advertising)
            add(R.drawable.advertise2)
            add(R.drawable.advertise3)
            add(R.drawable.advertise4)
            add(R.drawable.advertise5)
            add(R.drawable.advertise6)
        }

        binding.imAdvertising.setFactory {
            val imgView = ImageView(context)
            imgView.scaleType = ImageView.ScaleType.FIT_CENTER
            imgView.setPadding(8, 8, 8, 8)
            imgView
        }

        val imgIn = AnimationUtils.loadAnimation(
            context, android.R.anim.slide_in_left)
        binding.imAdvertising.inAnimation = imgIn

        val imgOut = AnimationUtils.loadAnimation(
            context, android.R.anim.slide_out_right)
        binding.imAdvertising.outAnimation = imgOut

        CoroutineScope(Dispatchers.Main).launch {

            while (this.isActive) {

                for (i in 0..5) {

                    binding.imAdvertising.setImageResource(listImgAdvertising.get(i))
                    delay(2500)

                }
            }
        }


    }

}


