package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.foroom.Helper.waitUntilVisible
import org.hamcrest.Matcher

/**
 * Create chat screen elements and basic actions.
 */
object CreateChatPage {
    private val createChatNavigationButton: Matcher<View> = withId(R.id.homeNavigationCreateChat)

    private val chatNameInput: Matcher<View> = withId(R.id.chatNameInput)
    private val chatImageChooser: Matcher<View> = withId(R.id.chatImageChooser)
    private val createChatButton: Matcher<View> = withId(R.id.createChatButton)

    fun tapCreateChatNavigation() = tapWhenVisible(createChatNavigationButton)

    fun waitUntilDisplayed(timeoutSec: Long = SCREEN_TIMEOUT_SEC) {
        onView(createChatButton).waitUntilVisible(timeoutSec)
        onView(chatNameInput).check(matches(isDisplayed()))
        onView(chatImageChooser).check(matches(isDisplayed()))
    }

    fun typeChatName(name: String) = typeInto(inputEditText(chatNameInput), name)

    fun waitForImagesLoaded() = waitForImagesLoaded(chatImageChooser)

    fun tapImage(index: Int) = tapImage(chatImageChooser, index)

    fun checkSelectedImage(index: Int) = checkSelectedImage(chatImageChooser, index)

    fun tapCreateChat() = tapWhenVisible(createChatButton)
}
