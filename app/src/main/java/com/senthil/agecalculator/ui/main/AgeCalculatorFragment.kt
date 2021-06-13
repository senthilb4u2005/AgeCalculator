package com.senthil.agecalculator.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.senthil.agecalculator.R
import com.senthil.agecalculator.databinding.FragmentAgeCalculatorBinding
import com.senthil.agecalculator.model.UserProfile
import com.senthil.agecalculator.ui.main.UserProfileFragment.Companion.PROFILE


class AgeCalculatorFragment : Fragment() {

    private var profile: UserProfile? = null
    private lateinit var binding: FragmentAgeCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            profile = it.getParcelable<UserProfile>(PROFILE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAgeCalculatorBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profile?.let {
            binding.lblFirstName.text = getString(R.string.out_first_name, it.firstName)
            binding.lblLastName.text = getString(R.string.out_last_name, it.lastName)
            binding.lblAge.text = getString(R.string.out_age, it.age, it.month, it.days)
        } ?: findNavController().popBackStack()
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}