package com.repair.lomasterapp.calendarDate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.repair.lomasterapp.databinding.CalendarDateFragmentBinding
import com.repair.lomasterapp.entity.OrderObj
import com.repair.lomasterapp.entity.UserPhone
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CalendarDateFragment : Fragment() {

    val repository = CalendarDateRepositoryImpl

    private var _binding: CalendarDateFragmentBinding? = null
    private val binding: CalendarDateFragmentBinding
        get() = _binding ?: throw RuntimeException("PickerDate == null")

    private val viewModel: CalendarDateViewModel by viewModels()

    @Inject
    lateinit var order: OrderObj

    @Inject
    lateinit var userPhone: UserPhone
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CalendarDateFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNewDate()

        setButtons()

    }

    private fun setNewDate() {

        binding.calendarOrder.date = order.let { viewModel.getDateOrder(it) }!!

    }

    private fun setButtons() {

        binding.calendarOrder.setOnDateChangeListener { calView: CalendarView, year: Int, month: Int, dayOfMonth: Int ->

            viewModel.setNewDateToOrder(order, year, month, dayOfMonth)

        }

        binding.btnChangeDateCalendar.setOnClickListener {

            findNavController().navigate(
                com.repair.lomasterapp.presentation.calendarDate.CalendarDateFragmentDirections.actionCalendarDateFragmentToNewRequestFragment2()
            )

        }

        binding.btnBackCalendar.setOnClickListener {
            findNavController().navigate(
                com.repair.lomasterapp.presentation.calendarDate.CalendarDateFragmentDirections.actionCalendarDateFragmentToNewRequestFragment2()
            )

        }

    }


}


