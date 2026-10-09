package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withChild
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.alternator.foroom.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DesignR

/**
 * Change password bottom sheet elements and basic actions.
 */
object ChangePasswordPage {
    // The sheet reuses the registration input IDs, so scope the inputs to the sheet content
    private val sheetContent: Matcher<View> = withChild(withId(R.id.repeatPasswordInput))

    private val passwordInput: Matcher<View> = allOf(withId(R.id.passwordInput), withParent(sheetContent))
    private val repeatPasswordInput: Matcher<View> = allOf(withId(R.id.repeatPasswordInput), withParent(sheetContent))
    private val confirmButton: Matcher<View> = withId(DesignR.id.actionButton)

    fun waitUntilDisplayed(timeoutSec: Long = SCREEN_TIMEOUT_SEC) {
        waitUntilShown(repeatPasswordInput, timeoutSec)
        onView(passwordInput).check(matches(isDisplayed()))
        onView(confirmButton).check(matches(isDisplayed()))
    }

    fun typePassword(password: String) = typeInto(inputEditText(passwordInput), password)

    fun typeRepeatPassword(password: String) = typeInto(inputEditText(repeatPasswordInput), password)

    fun tapConfirm() = tapWhenVisible(confirmButton)
}
