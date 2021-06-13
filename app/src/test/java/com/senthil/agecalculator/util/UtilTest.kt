package com.senthil.agecalculator.util

import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.time.LocalDate

@RunWith(JUnit4::class)
class UtilTest {

    @Test
    fun test_given_valid_date_return_valid_local_date() {
        val date = "20-06-1983"
        val localDate = Util.getLocalDate(date);
        assert(localDate != null)
    }

    @Test
    fun test_given_invalid_date_return_null() {
        val date = "20-06-1"
        val localDate = Util.getLocalDate(date);
        assert(localDate == null)
    }

    @Test
    fun test_given_valid_local_date_return_right_age_month_days() {
        val dob_local_date = Util.getLocalDate("20-06-1983")
        val current_local_date = Util.getLocalDate("13-06-2021")
        mockkStatic(LocalDate::class)
        every { LocalDate.now() } returns current_local_date
        dob_local_date?.let {
            val (age, month, days) = Util.getYearsMonthDays(dob_local_date)
            assert(age == 37)
            assert(month == 11)
            assert(days == 24)
        }


    }


}