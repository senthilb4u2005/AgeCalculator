package com.senthil.agecalculator.util

import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class Util {
    companion object {
        private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        fun getLocalDate(dateStr: String): LocalDate? {
            return try {
                LocalDate.parse(dateStr, formatter)
            } catch (e: Exception) {
                null
            }
        }

        fun getYearsMonthDays(date: LocalDate): Triple<Int, Int, Int> {
            val period = Period.between(date, LocalDate.now())
            return Triple(period.years, period.months, period.days)
        }
    }
}