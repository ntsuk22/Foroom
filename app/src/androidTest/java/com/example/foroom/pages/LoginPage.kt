package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withChild
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.foroom.Helper.waitUntilVisible
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import com.example.design_system.R as DesignR

/**
 * Login screen elements and basic actions.
 */
object LoginPage {
    // Login and registration share input/button IDs, so every matcher is scoped to the login form
    private val loginForm: Matcher<View> = withChild(withId(R.id.logInButton))

    private val userNameInput: Matcher<View> = allOf(withId(R.id.userNameInput), withParent(loginForm))
    private val passwordInput: Matcher<View> = allOf(withId(R.id.passwordInput), withParent(loginForm))
    private val logInButton: Matcher<View> = allOf(withId(R.id.logInButton), withParent(loginForm))
    private val signUpButton: Matcher<View> = allOf(withId(R.id.signUpButton), withParent(loginForm))

    private val userNameEditText = inputEditText(userNameInput)
    private val passwordEditText = inputEditText(passwordInput)
    private val userNameError = inputDescription(userNameInput)
    private val passwordError = inputDescription(passwordInput)

    fun waitUntilDisplayed(timeoutSec: Long = SCREEN_TIMEOUT_SEC) {
        onView(logInButton).waitUntilVisible(timeoutSec)
        onView(userNameInput).check(matches(isDisplayed()))
        onView(passwordInput).check(matches(isDisplayed()))
        onView(signUpButton).check(matches(isDisplayed()))
    }

    fun typeUserName(userName: String) = typeInto(userNameEditText, userName)

    fun typePassword(password: String) = typeInto(passwordEditText, password)

    fun tapLogIn() {
        onView(logInButton).waitUntilVisible(VIEW_TIMEOUT_SEC).perform(click())
    }

    fun tapSignUp() {
        onView(signUpButton).waitUntilVisible(VIEW_TIMEOUT_SEC).perform(click())
    }

    fun waitForUserNameError(timeoutSec: Long = RESPONSE_TIMEOUT_SEC) = waitForError(userNameError, timeoutSec)

    fun waitForPasswordError(timeoutSec: Long = RESPONSE_TIMEOUT_SEC) = waitForError(passwordError, timeoutSec)

    fun checkUserNameErrorNotDisplayed() {
        onView(userNameError).check(matches(not(isDisplayed())))
    }

    private fun typeInto(editText: Matcher<View>, text: String) {
        onView(editText).waitUntilVisible(VIEW_TIMEOUT_SEC).perform(replaceText(text), closeSoftKeyboard())
    }

    private fun waitForError(error: Matcher<View>, timeoutSec: Long) {
        onView(error).waitUntilVisible(timeoutSec).check(matches(not(withText(""))))
    }
}

/** The `inputEditText` inside the given custom `Input`. */
internal fun inputEditText(input: Matcher<View>): Matcher<View> =
    allOf(withId(DesignR.id.inputEditText), isDescendantOfA(input))

/** The `descriptionTextView` (error/info text) inside the given custom `Input`. */
internal fun inputDescription(input: Matcher<View>): Matcher<View> =
    allOf(withId(DesignR.id.descriptionTextView), isDescendantOfA(input))

internal const val VIEW_TIMEOUT_SEC = 5L
internal const val SCREEN_TIMEOUT_SEC = 15L
internal const val RESPONSE_TIMEOUT_SEC = 15L
