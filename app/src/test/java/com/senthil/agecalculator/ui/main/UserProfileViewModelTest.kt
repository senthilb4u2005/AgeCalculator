package com.senthil.agecalculator.ui.main

import android.text.TextUtils
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.senthil.agecalculator.getOrAwaitValue
import com.senthil.agecalculator.util.Util
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.time.LocalDate


class UserProfileViewModelTest {

    lateinit var subject: UserProfileViewModel

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        subject = UserProfileViewModel()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun given_invalid_first_name_and_last_name_and_dob_live_date_value_shoudl_be_true() {
         mockkStatic(TextUtils::class)
        every { TextUtils.isEmpty(any()) } returns true
        subject.calculateAndDisplayAge("","","")
        val firNameResult = subject.isFirstNameInValid.getOrAwaitValue()
        val lastNameResult = subject.isLastNameInValid.getOrAwaitValue()
        assertEquals(firNameResult.getContentIfNotHandled() , true)
        assertEquals(lastNameResult.getContentIfNotHandled() , true)
    }

    @Test
    fun given_valid_data_user_file_observe_valid_user_profile(){
        mockkStatic(TextUtils::class)
        every { TextUtils.isEmpty(any()) } returns false
        val current_local_date = Util.getLocalDate("13-06-2021")
        mockkStatic(LocalDate::class)
        every { LocalDate.now() } returns current_local_date
        subject.calculateAndDisplayAge("Senthil", "Mani", "20-06-1983")
        val result = subject.userProfile.getOrAwaitValue()
        assertNotNull(result)

    }

}