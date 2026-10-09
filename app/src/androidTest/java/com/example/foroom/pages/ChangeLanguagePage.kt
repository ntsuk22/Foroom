package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import org.hamcrest.Matcher

/**
 * Change language bottom sheet elements and basic actions.
 */
object ChangeLanguagePage {
    private val georgianButton: Matcher<View> = withId(R.id.languageButtonGeo)
    private val englishButton: Matcher<View> = withId(R.id.languageButtonEng)

    fun waitUntilDisplayed(timeoutSec: Long = SCREEN_TIMEOUT_SEC) {
        waitUntilShown(georgianButton, timeoutSec)
        onView(englishButton).check(matches(isDisplayed()))
    }

    fun tapGeorgian() = tapWhenVisible(georgianButton)

    fun tapEnglish() = tapWhenVisible(englishButton)
}
