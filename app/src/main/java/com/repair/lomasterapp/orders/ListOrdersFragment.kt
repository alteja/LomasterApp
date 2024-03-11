package com.repair.lomasterapp.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import com.repair.lomasterapp.R
import com.repair.lomasterapp.databinding.ListOrdersFragmentBinding
import com.repair.lomasterapp.entity.UserPhone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class ListOrdersFragment : Fragment() {

    private val viewModel: ListOrdersViewModel by viewModels()

    private var _binding: ListOrdersFragmentBinding? = null
    private val binding: ListOrdersFragmentBinding
        get() = _binding ?: throw RuntimeException("ListOrdersFragment == null")

    @Inject
    lateinit var userPhone: UserPhone

    private lateinit var ordersListAdapter: OrdersListAdapter

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

        _binding = ListOrdersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.pbListOrders.visibility = View.VISIBLE

        setProgressBar()


        //перед формированием макета, подгрузим данные полей заказа с сервера.
        CoroutineScope(coroutineContext).launch {

            context?.let { viewModel.setOrderInformation(userPhone.toString()) }

            setupRecyclerView()

            observeViewModel()

            binding.pbListOrders.visibility = View.INVISIBLE
        }


        setupClickListener()

    }

    private fun setProgressBar() {


        binding.pbListOrders.apply {
            // Set Progress
            progress = 15f
            // or with animation
            setProgressWithAnimation(100f, 1500) // =1s

            // Set Progress Max
            progressMax = 100f

            // Set ProgressBar Color
            progressBarColor = context.getColor(R.color.color_green)
            // or with gradient
            progressBarColorStart = context.getColor(R.color.light_green)
            progressBarColorEnd = context.getColor(R.color.dark_green)
            progressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM

            // Set background ProgressBar Color
            backgroundProgressBarColor = context.getColor(R.color.light_green)
            // or with gradient
            backgroundProgressBarColorStart = context.getColor(R.color.light_green)
            backgroundProgressBarColorEnd = context.getColor(R.color.light_green)
            backgroundProgressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM

            // Set Width
            progressBarWidth = 20f // in DP
            backgroundProgressBarWidth = 22f // in DP

            // Other
            roundBorder = true
            startAngle = 10f
            progressDirection = CircularProgressBar.ProgressDirection.TO_RIGHT
        }
    }


    private fun setupRecyclerView() {

        with(binding.rvListRequests) {
            ordersListAdapter = OrdersListAdapter()
            adapter = ordersListAdapter
            //recycledViewPool.setMaxRecycledViews(
            //    R.layout.item_list_orders_fragment,
            //    OrdersListAdapter.MAX_POOL_SIZE
            // )
        }


        //   setupSwipeListener(binding.rvListRequests)
    }

    private fun observeViewModel() {

        viewModel.ordersData.observe(viewLifecycleOwner) {

            with(ordersListAdapter) {

                setOrdersList(viewModel.ordersData.value)
                submitList(it)

            }
        }

    }


    private fun setupClickListener() {

        binding.btnListRequestBack.setOnClickListener {

            findNavController().navigate(
                com.repair.lomasterapp.presentation.requests.ListOrdersFragmentDirections.actionListOrdersFragmentToMainFragment()
            )

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job.cancel()
    }

}