package com.senthil.agecalculator.ui.main

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.senthil.agecalculator.model.Event
import com.senthil.agecalculator.model.UserProfile
import com.senthil.agecalculator.util.Util

class UserProfileViewModel : ViewModel() {
    private var _isFirstNameInValid: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val isFirstNameInValid: LiveData<Event<Boolean>>
        get() = _isFirstNameInValid

    private var _isLastNameInValid = MutableLiveData<Event<Boolean>>()
    val isLastNameInValid: LiveData<Event<Boolean>>
        get() = _isLastNameInValid

    private val _userProfile = MutableLiveData<Event<UserProfile>>()
    val userProfile: LiveData<Event<UserProfile>>
        get() = _userProfile

    fun calculateAndDisplayAge(firstName: String, lastName: String, dob: String) {
        var isValid = true

        if (TextUtils.isEmpty(firstName)) {
            isValid = false
            _isFirstNameInValid.value = Event(true)
        }

        if (TextUtils.isEmpty(lastName)) {
            isValid = false
            _isLastNameInValid.value = Event(true)
        }

        val localDate = Util.getLocalDate(dob)
        localDate?.let {
            if (isValid) {
                val (age, month, days) = Util.getYearsMonthDays(localDate)
                _userProfile.value = Event(UserProfile(firstName, lastName, age, month, days))
            }
        }


    }


}