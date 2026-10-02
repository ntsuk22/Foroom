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
import com.alternator.foroom.R
import com.example.design_system.components.image_chooser.ImageChooserItemView
import com.example.design_system.components.image_chooser.ImageChooserListView
import androidx.test.espresso.util.TreeIterables
import com.example.foroom.Helper.waitUntilVisible
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.assertEquals
import java.util.concurrent.TimeoutException

/**
 * Registration screen elements and basic actions, plus the home screen
 * elements used to confirm a successful registration.
 */
object RegistrationPage {
    // Registration reuses the login IDs, so every matcher is scoped to the registration form
    private val registrationForm: Matcher<View> = withChild(withId(R.id.repeatPasswordInput))

    private val userNameInput: Matcher<View> = allOf(withId(R.id.userNameInput), withParent(registrationForm))
    private val passwordInput: Matcher<View> = allOf(withId(R.id.passwordInput), withParent(registrationForm))
    private val repeatPasswordInput: Matcher<View> = allOf(withId(R.id.repeatPasswordInput), withParent(registrationForm))
    private val avatarList: Matcher<View> = allOf(withId(R.id.listView), withParent(registrationForm))
    private val signUpButton: Matcher<View> = allOf(withId(R.id.signUpButton), withParent(registrationForm))

    private val homeContainer: Matcher<View> = withId(R.id.homeContainer)
    private val navBar: Matcher<View> = withId(R.id.navBar)

    fun waitUntilDisplayed(timeoutSec: Long = SCREEN_TIMEOUT_SEC) {
        onView(repeatPasswordInput).waitUntilVisible(timeoutSec)
        onView(userNameInput).check(matches(isDisplayed()))
        onView(passwordInput).check(matches(isDisplayed()))
        onView(avatarList).check(matches(isDisplayed()))
        onView(signUpButton).check(matches(isDisplayed()))
    }

    fun typeUserName(userName: String) = typeInto(inputEditText(userNameInput), userName)

    fun typePassword(password: String) = typeInto(inputEditText(passwordInput), password)

    fun typeRepeatPassword(password: String) = typeInto(inputEditText(repeatPasswordInput), password)

    /**
     * Avatars are loaded asynchronously; until they arrive the list shows
     * placeholders and ignores clicks.
     */
    fun waitForAvatarsLoaded(timeoutSec: Long = RESPONSE_TIMEOUT_SEC) {
        val endTime = System.currentTimeMillis() + timeoutSec * 1000
        while (true) {
            var loaded = false
            onView(avatarList).check { view, noViewFoundException ->
                noViewFoundException?.let { throw it }
                val list = view as ImageChooserListView
                loaded = list.isChoosingEnabled && list.images.isNotEmpty()
            }
            if (loaded) return
            if (System.currentTimeMillis() > endTime) throw TimeoutException("Avatars were not loaded in $timeoutSec sec")
            Thread.sleep(POLL_INTERVAL_MS)
        }
    }

    fun tapAvatar(index: Int) {
        onView(avatarAt(index)).waitUntilVisible(VIEW_TIMEOUT_SEC).perform(click())
    }

    fun checkSelectedAvatar(index: Int) {
        onView(avatarList).check { view, noViewFoundException ->
            noViewFoundException?.let { throw it }
            assertEquals("Selected avatar index", index, (view as ImageChooserListView).selectedIndex)
        }
    }

    fun tapSignUp() {
        onView(signUpButton).waitUntilVisible(VIEW_TIMEOUT_SEC).perform(click())
    }

    fun waitForHomeScreen(timeoutSec: Long = RESPONSE_TIMEOUT_SEC) {
        onView(navBar).waitUntilVisible(timeoutSec)
        onView(homeContainer).check(matches(isDisplayed()))
    }

    private fun typeInto(editText: Matcher<View>, text: String) {
        onView(editText).waitUntilVisible(VIEW_TIMEOUT_SEC).perform(replaceText(text), closeSoftKeyboard())
    }

    /**
     * Avatar item at [index] (row by row) inside the avatar list. Stateless, unlike the
     * Helper `withIndex`, so it can be matched again by a wait followed by an action.
     */
    private fun avatarAt(index: Int): Matcher<View> = allOf(
        isDescendantOfA(avatarList),
        object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("avatar item at index $index")
            }

            override fun matchesSafely(view: View): Boolean {
                if (view !is ImageChooserItemView) return false
                var parent = view.parent
                while (parent != null && parent !is ImageChooserListView) parent = parent.parent
                val list = parent as? ImageChooserListView ?: return false
                val items = TreeIterables.breadthFirstViewTraversal(list).filterIsInstance<ImageChooserItemView>()
                return items.indexOf(view) == index
            }
        }
    )

    private const val POLL_INTERVAL_MS = 100L
}
