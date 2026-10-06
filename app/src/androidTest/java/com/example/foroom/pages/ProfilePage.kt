package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.foroom.Helper.waitUntilVisible
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

/**
 * Profile tab elements and basic actions.
 */
object ProfilePage {
    private val profileNavigationButton: Matcher<View> = withId(R.id.homeNavigationProfile)

    private val changePasswordItem: Matcher<View> = withId(R.id.changePasswordItem)
    private val changeLanguageItem: Matcher<View> = withId(R.id.changeLanguageItem)
    private val signOutItem: Matcher<View> = withId(R.id.signOutItem)

    fun tapProfileNavigation() = tapWhenVisible(profileNavigationButton)

    fun waitUntilDisplayed(timeoutSec: Long = SCREEN_TIMEOUT_SEC) {
        onView(changeLanguageItem).waitUntilVisible(timeoutSec)
        onView(changePasswordItem).check(matches(isDisplayed()))
        onView(signOutItem).check(matches(isDisplayed()))
    }

    fun tapChangePassword() = tapWhenVisible(changePasswordItem)

    fun tapChangeLanguage() = tapWhenVisible(changeLanguageItem)

    fun tapSignOut() = tapWhenVisible(signOutItem)

    /** Language changes recreate the activity, so the label is awaited rather than checked once. */
    fun waitForChangeLanguageLabel(label: String, timeoutSec: Long = SCREEN_TIMEOUT_SEC) =
        waitForLabel(changeLanguageItem, label, timeoutSec)

    fun waitForSignOutLabel(label: String, timeoutSec: Long = SCREEN_TIMEOUT_SEC) =
        waitForLabel(signOutItem, label, timeoutSec)

    private fun waitForLabel(item: Matcher<View>, label: String, timeoutSec: Long) {
        onView(allOf(withText(label), isDescendantOfA(item))).waitUntilVisible(timeoutSec)
    }
}
