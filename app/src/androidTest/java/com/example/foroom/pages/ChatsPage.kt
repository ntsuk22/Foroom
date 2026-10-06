package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
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
import com.example.design_system.R as DesignR

/**
 * Home screen with the chat list, and the opened chat screen.
 */
object ChatsPage {
    private val homeContainer: Matcher<View> = withId(R.id.homeContainer)
    private val navBar: Matcher<View> = withId(R.id.navBar)

    private val searchChatInput: Matcher<View> = withId(R.id.searchChatInput)
    private val chatsRecyclerView: Matcher<View> = withId(R.id.chatsRecyclerView)

    // Create chat screen also has a chat header and a close button, so scope them to the chat screen
    private val chatScreen: Matcher<View> = withChild(withId(R.id.messagesRecyclerView))
    private val chatHeader: Matcher<View> = allOf(withId(R.id.chatHeaderView), withParent(chatScreen))
    private val chatCloseButton: Matcher<View> = allOf(withId(R.id.closeButton), withParent(chatScreen))

    fun waitForHomeScreen(timeoutSec: Long = RESPONSE_TIMEOUT_SEC) {
        onView(navBar).waitUntilVisible(timeoutSec)
        onView(homeContainer).check(matches(isDisplayed()))
    }

    fun waitUntilDisplayed(timeoutSec: Long = SCREEN_TIMEOUT_SEC) {
        onView(searchChatInput).waitUntilVisible(timeoutSec)
        onView(chatsRecyclerView).check(matches(isDisplayed()))
    }

    fun waitForOpenedChat(name: String, timeoutSec: Long = RESPONSE_TIMEOUT_SEC) {
        onView(allOf(withId(DesignR.id.chatNameTextView), withText(name), isDescendantOfA(chatHeader)))
            .waitUntilVisible(timeoutSec)
    }

    fun tapCloseChat() = tapWhenVisible(chatCloseButton)

    fun typeSearch(text: String) = typeInto(inputEditText(searchChatInput), text)

    fun waitForChatCard(name: String, timeoutSec: Long = RESPONSE_TIMEOUT_SEC) {
        onView(allOf(withId(DesignR.id.chatTitleTextView), withText(name), isDescendantOfA(chatsRecyclerView)))
            .waitUntilVisible(timeoutSec)
    }
}
