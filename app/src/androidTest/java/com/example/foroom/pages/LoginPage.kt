package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withChild
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not

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
    private val homeNavBar: Matcher<View> = withId(R.id.navBar)

    fun waitUntilDisplayed(timeoutSec: Long = SCREEN_TIMEOUT_SEC) {
        waitUntilShown(logInButton, timeoutSec)
        onView(userNameInput).check(matches(isDisplayed()))
        onView(passwordInput).check(matches(isDisplayed()))
        onView(signUpButton).check(matches(isDisplayed()))
    }

    fun typeUserName(userName: String) = typeInto(userNameEditText, userName)

    fun typePassword(password: String) = typeInto(passwordEditText, password)

    fun tapLogIn() = tapWhenVisible(logInButton)

    fun tapSignUp() = tapWhenVisible(signUpButton)

    fun waitForUserNameError(timeoutSec: Long = RESPONSE_TIMEOUT_SEC) = waitForError(userNameError, timeoutSec)

    fun waitForPasswordError(timeoutSec: Long = RESPONSE_TIMEOUT_SEC) = waitForError(passwordError, timeoutSec)

    fun checkUserNameErrorNotDisplayed() {
        onView(userNameError).check(matches(not(isDisplayed())))
    }

    /** Waits for the outcome of a login attempt. An unknown user also gets a password error, so it is checked first. */
    fun waitForLoginResult(timeoutSec: Long = RESPONSE_TIMEOUT_SEC): LoginResult =
        when (waitForAnyDisplayed(listOf(homeNavBar, userNameError, passwordError), timeoutSec)) {
            0 -> LoginResult.HOME
            1 -> LoginResult.UNKNOWN_USER
            else -> LoginResult.WRONG_PASSWORD
        }

    enum class LoginResult { HOME, UNKNOWN_USER, WRONG_PASSWORD }

    private fun waitForError(error: Matcher<View>, timeoutSec: Long) {
        waitUntilShown(error, timeoutSec).check(matches(not(withText(""))))
    }
}
