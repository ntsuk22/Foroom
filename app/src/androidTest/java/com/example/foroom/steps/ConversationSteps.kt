package com.example.foroom.steps

import com.example.foroom.pages.ConversationPage

/**
 * Reusable conversation flows and checks for an opened chat.
 */
object ConversationSteps {

    fun sendMessage(text: String) {
        ConversationPage.typeMessage(text)
        ConversationPage.tapSend()
        ConversationPage.waitUntilMessageSent()
    }

    fun sendMessages(texts: List<String>) {
        texts.forEach(::sendMessage)
    }

    fun checkMessageDisplayed(text: String, sender: String) {
        ConversationPage.waitForMessage(text, sender)
    }

    /** Swipes to older messages (bounded) and verifies the message and its sender. */
    fun swipeToOlderMessage(text: String, sender: String) {
        val found = ConversationPage.swipeUntilMessageDisplayed(text, sender, MAX_SWIPES)
        if (!found) throw AssertionError("Message '$text' from '$sender' not found after $MAX_SWIPES swipes")
        checkMessageDisplayed(text, sender)
    }

    private const val MAX_SWIPES = 15
}
