package com.senthil.agecalculator.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.senthil.agecalculator.R
import com.senthil.agecalculator.databinding.FragmentUserProfileBinding
import com.senthil.agecalculator.model.UserProfile


/**
 * A simple [Fragment] subclass.
 * Use the [UserProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserProfileFragment : Fragment() {

    private lateinit var viewModel: UserProfileViewModel
    private lateinit var binding: FragmentUserProfileBinding
    private lateinit var cacheUserProfile: UserProfile

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtFirstName.doOnTextChanged { _, _, _, _ -> binding.lblFirstName.error = null }
        binding.txtLastName.doOnTextChanged { _, _, _, _ -> binding.lblLastName.error = null }

        binding.btnCalculateAge.setOnClickListener {

            with(binding) {
                viewModel.calculateAndDisplayAge(
                    txtFirstName.text.toString(),
                    txtLastName.text.toString(),
                    getDob()
                )

            }
        }
    }

    private fun getDob(): String {
        val date = binding.datePicker.dayOfMonth.toString().let { it ->
            when (it.length) {
                1 -> "0$it"
                else -> it
            }
        }

        val month = binding.datePicker.month.toString().let {
            when (it.length) {
                1 -> "0$it"
                else -> it
            }
        }

        val year = binding.datePicker.year

        return "$date-$month-$year"


    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserProfileViewModel::class.java)

        viewModel.isFirstNameInValid.observe(
            viewLifecycleOwner,
            Observer { event ->
                event.getContentIfNotHandled()?.let { isNotValid ->
                    if (isNotValid) binding.lblFirstName.error =
                        getString(R.string.error_first_name)
                }

            })

        viewModel.isLastNameInValid.observe(
            viewLifecycleOwner,
            Observer { isNotValid ->
                isNotValid.getContentIfNotHandled()?.let { isNotValid ->
                    if (isNotValid) binding.lblLastName.error = getString(R.string.error_last_name)
                }

            })

        viewModel.userProfile.observe(
            viewLifecycleOwner,
            Observer { event ->
                event.getContentIfNotHandled()?.let { userProfile ->
                    val bundle = bundleOf(PROFILE to userProfile)
                    findNavController().navigate(
                        R.id.action_userProfileFragment_to_ageCalculatorFragment,
                        bundle
                    )
                }
            })
    }



    companion object {
        fun newInstance() = UserProfileFragment()
        const val PROFILE = "PROFILE"
    }
}