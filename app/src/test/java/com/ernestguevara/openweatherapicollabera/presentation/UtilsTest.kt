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
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

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
}