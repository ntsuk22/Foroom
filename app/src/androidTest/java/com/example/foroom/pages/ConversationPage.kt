package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withChild
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.foroom.Helper.swiper
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DesignR

/**
 * Opened chat (conversation) screen elements and basic actions.
 */
object ConversationPage {
    // Create chat screen also has a chat header and a close button, so scope them to the chat screen
    private val chatScreen: Matcher<View> = withChild(withId(R.id.messagesRecyclerView))
    private val chatHeader: Matcher<View> = allOf(withId(R.id.chatHeaderView), withParent(chatScreen))
    private val closeButton: Matcher<View> = allOf(withId(R.id.closeButton), withParent(chatScreen))
    private val messagesList: Matcher<View> = allOf(withId(R.id.messagesRecyclerView), withParent(chatScreen))
    private val messageInput: Matcher<View> = allOf(withId(R.id.messageInput), withParent(chatScreen))
    private val messageEditText: Matcher<View> = inputEditText(messageInput)
    private val sendButton: Matcher<View> = allOf(withId(R.id.sendMessageButton), isDescendantOfA(messageInput))

    fun waitForChatTitle(name: String, timeoutSec: Long = RESPONSE_TIMEOUT_SEC) {
        waitUntilShown(allOf(withId(DesignR.id.chatNameTextView), withText(name), isDescendantOfA(chatHeader)), timeoutSec)
    }

    fun tapClose() = tapWhenVisible(closeButton)

    fun typeMessage(text: String) = typeInto(messageEditText, text)

    /** The send button stays disabled until the chat is connected and while a message is being sent. */
    fun tapSend() {
        waitUntilMatches(sendButton, isEnabled())
        onView(sendButton).perform(click())
    }

    /** A sent message clears the input and re-enables the send button. */
    fun waitUntilMessageSent() {
        waitUntilMatches(messageEditText, withText(""))
        waitUntilMatches(sendButton, isEnabled())
    }

    fun waitForMessage(text: String, sender: String, timeoutSec: Long = RESPONSE_TIMEOUT_SEC) {
        waitUntilShown(message(text, sender), timeoutSec)
    }

    /**
     * Swipes the message list towards older messages until the message is displayed, at most
     * [maxSwipes] times. Older pages are loaded asynchronously when the top of the list is reached.
     */
    fun swipeUntilMessageDisplayed(text: String, sender: String, maxSwipes: Int): Boolean {
        val target = message(text, sender)
        repeat(maxSwipes) {
            if (waitUntilDisplayed(target, PAGE_LOAD_TIMEOUT_SEC)) return true
            swipeTowardsOlderMessages()
        }
        return waitUntilDisplayed(target, PAGE_LOAD_TIMEOUT_SEC)
    }

    /**
     * Drags downwards inside the visible part of the message list (between the header and the
     * input), so the gesture adapts to the list's position and the device's screen size.
     * The list uses a reversed layout, so dragging down reveals older messages.
     */
    private fun swipeTowardsOlderMessages() {
        var visibleTop = 0
        var visibleBottom = 0
        onView(messagesList).check { view, noViewFoundException ->
            noViewFoundException?.let { throw it }
            val location = IntArray(2)
            view.getLocationOnScreen(location)
            visibleTop = location[1] + view.paddingTop
            visibleBottom = location[1] + view.height - view.paddingBottom
        }
        val visibleHeight = visibleBottom - visibleTop
        swiper(
            visibleTop + visibleHeight / SWIPE_EDGE_DIVIDER,
            visibleBottom - visibleHeight / SWIPE_EDGE_DIVIDER,
            SWIPE_DURATION_MS
        )
    }

    /** A message text inside the message bubble that also shows the given sender name. */
    private fun message(text: String, sender: String): Matcher<View> = allOf(
        withId(DesignR.id.messageTextView),
        withText(text),
        isDescendantOfA(
            allOf(
                withId(R.id.messageView),
                hasDescendant(allOf(withId(DesignR.id.userNameTextView), withText(sender)))
            )
        ),
        isDescendantOfA(messagesList)
    )

    private const val PAGE_LOAD_TIMEOUT_SEC = 2L
    private const val SWIPE_EDGE_DIVIDER = 5
    private const val SWIPE_DURATION_MS = 300
}
