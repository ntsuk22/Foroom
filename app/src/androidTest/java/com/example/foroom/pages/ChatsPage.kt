package com.example.foroom.pages

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DesignR

/**
 * Home screen with the chat list.
 */
object ChatsPage {
    private val homeContainer: Matcher<View> = withId(R.id.homeContainer)
    private val navBar: Matcher<View> = withId(R.id.navBar)

    private val searchChatInput: Matcher<View> = withId(R.id.searchChatInput)
    private val chatsRecyclerView: Matcher<View> = withId(R.id.chatsRecyclerView)

    fun waitForHomeScreen(timeoutSec: Long = RESPONSE_TIMEOUT_SEC) {
        waitUntilShown(navBar, timeoutSec)
        onView(homeContainer).check(matches(isDisplayed()))
    }

    fun waitUntilDisplayed(timeoutSec: Long = SCREEN_TIMEOUT_SEC) {
        waitUntilShown(searchChatInput, timeoutSec)
        onView(chatsRecyclerView).check(matches(isDisplayed()))
    }

    fun typeSearch(text: String) = typeInto(inputEditText(searchChatInput), text)

    fun waitForChatCard(name: String, timeoutSec: Long = RESPONSE_TIMEOUT_SEC) {
        waitUntilShown(chatCardTitle(name), timeoutSec)
    }

    fun isChatCardDisplayed(name: String, timeoutSec: Long): Boolean =
        waitUntilDisplayed(chatCardTitle(name), timeoutSec)

    /**
     * A chat is opened with the message button on its card. The search bar overlaps the top of the
     * list, so the card is first scrolled fully into the list's visible area.
     */
    fun tapOpenChat(name: String) {
        waitUntilShown(chatCardTitle(name), RESPONSE_TIMEOUT_SEC)
        onView(chatsRecyclerView).perform(
            RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                hasDescendant(allOf(withId(DesignR.id.chatTitleTextView), withText(name)))
            )
        )
        tapWhenVisible(allOf(withId(DesignR.id.sendMessageButton), hasSibling(chatCardTitle(name))))
    }

    // Matched by the exact title, so chats whose titles only contain the name are ignored
    private fun chatCardTitle(name: String): Matcher<View> =
        allOf(withId(DesignR.id.chatTitleTextView), withText(name), isDescendantOfA(chatsRecyclerView))
}
