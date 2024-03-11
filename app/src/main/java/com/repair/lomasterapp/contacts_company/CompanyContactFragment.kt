package com.repair.lomasterapp.contacts_company

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.repair.lomasterapp.databinding.CompanyContactFragmentBinding
import com.repair.lomasterapp.entity.UserPhone
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CompanyContactFragment : Fragment() {

    @Inject
    lateinit var userPhone: UserPhone

    private var _binding: CompanyContactFragmentBinding? = null
    private val binding: CompanyContactFragmentBinding
        get() = _binding ?: throw RuntimeException("CompanyContactFragmentBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CompanyContactFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        buttonsNavigate()

    }

    private fun buttonsNavigate() {

        binding.btnNextCompany.setOnClickListener {
            findNavController().navigate(
                com.repair.lomasterapp.presentation.contacts_company.CompanyContactFragmentDirections.actionCompanyContactFragmentToMainFragment()
            )
        }

        binding.imPhone1Company.setOnClickListener{
            makeCalling(binding.tvNumberPhoneCompany1.text.toString())
        }

        binding.imPhone2Company.setOnClickListener{
            makeCalling(binding.tvNumberPhoneCompany2.text.toString())
        }

        binding.imPhone3Company.setOnClickListener{
            makeCalling(binding.tvNumberPhoneCompany3.text.toString())
        }

        binding.tvNumberPhoneCompany1.setOnClickListener {
            makeCalling(binding.tvNumberPhoneCompany1.text.toString())
        }

        binding.tvNumberPhoneCompany2.setOnClickListener {
            makeCalling(binding.tvNumberPhoneCompany2.text.toString())
        }

        binding.tvNumberPhoneCompany3.setOnClickListener {
            makeCalling(binding.tvNumberPhoneCompany3.text.toString())
        }

    }

    private fun makeCalling(numberPhone: String) {

        if (!TextUtils.isEmpty(numberPhone)) {

            if (!TextUtils.isEmpty(numberPhone)) {

                val intent = Intent(Intent.ACTION_DIAL);
                intent.data = Uri.parse("tel:$numberPhone")
                startActivity(intent)

            }

        } else {

            Toast.makeText(context, "Enter a phone number", Toast.LENGTH_SHORT).show()

        }

    }
}