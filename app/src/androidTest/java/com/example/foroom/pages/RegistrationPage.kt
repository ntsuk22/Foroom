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

/**
 * Registration screen elements and basic actions.
 */
object RegistrationPage {
    // Registration reuses the login IDs, so every matcher is scoped to the registration form
    private val registrationForm: Matcher<View> = withChild(withId(R.id.repeatPasswordInput))

    private val userNameInput: Matcher<View> = allOf(withId(R.id.userNameInput), withParent(registrationForm))
    private val passwordInput: Matcher<View> = allOf(withId(R.id.passwordInput), withParent(registrationForm))
    private val repeatPasswordInput: Matcher<View> = allOf(withId(R.id.repeatPasswordInput), withParent(registrationForm))
    private val avatarList: Matcher<View> = allOf(withId(R.id.listView), withParent(registrationForm))
    private val signUpButton: Matcher<View> = allOf(withId(R.id.signUpButton), withParent(registrationForm))

    fun waitUntilDisplayed(timeoutSec: Long = SCREEN_TIMEOUT_SEC) {
        waitUntilShown(repeatPasswordInput, timeoutSec)
        onView(userNameInput).check(matches(isDisplayed()))
        onView(passwordInput).check(matches(isDisplayed()))
        onView(avatarList).check(matches(isDisplayed()))
        onView(signUpButton).check(matches(isDisplayed()))
    }

    fun typeUserName(userName: String) = typeInto(inputEditText(userNameInput), userName)

    fun typePassword(password: String) = typeInto(inputEditText(passwordInput), password)

    fun typeRepeatPassword(password: String) = typeInto(inputEditText(repeatPasswordInput), password)

    fun waitForAvatarsLoaded() = waitForImagesLoaded(avatarList)

    fun tapAvatar(index: Int) = tapImage(avatarList, index)

    fun checkSelectedAvatar(index: Int) = checkSelectedImage(avatarList, index)

    fun tapSignUp() = tapWhenVisible(signUpButton)

    fun waitForHomeScreen() = ChatsPage.waitForHomeScreen()
}
