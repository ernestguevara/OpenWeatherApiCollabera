package com.ernestguevara.openweatherapicollabera.presentation

import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AlignmentSpan
import com.ernestguevara.openweatherapicollabera.util.*
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.time.LocalDate
import java.time.ZoneId

@RunWith(JUnit4::class)
class UtilsTest {
    @Test
    fun testIsNotNullOrNotEmpty() {
        val nullString: String? = null
        val emptyString = ""
        val nonEmptyString = "Test"

        assertThat(nullString.isNotNullOrNotEmpty()).isEqualTo(false)
        assertThat(emptyString.isNotNullOrNotEmpty()).isEqualTo(false)
        assertThat(nonEmptyString.isNotNullOrNotEmpty()).isEqualTo(true)
    }

    @Test
    fun testSetLocationName() {
        val city = "London"
        val country = "United Kingdom"
        val expectedLocationName = "London, United Kingdom"

        val actualLocationName = setLocationName(city, country)

        assertThat(expectedLocationName).isEqualTo(actualLocationName)
    }

    @Test
    fun testConvertLongToTimeString() {
        val timeInMillis: Long = 1621346400000 // Example: May 19, 2021 00:00:00 UTC
        val format = "yyyy-MM-dd HH:mm:ss"
        val expectedTimeString = "2021-05-19 00:00:00"

        val actualTimeString = convertLongToTimeString(timeInMillis, format)

        assertThat(expectedTimeString).isEqualTo(actualTimeString)
    }

    @Test
    fun testGetCurrentDayLong() {
        val currentDate = LocalDate.now()
        val expectedTimeInMillis =
            currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val actualTimeInMillis = getCurrentDayLong()

        assertThat(expectedTimeInMillis).isEqualTo(actualTimeInMillis)
    }
}