package com.example.foroom.steps

import com.example.foroom.pages.ChatsPage
import com.example.foroom.pages.ConversationPage
import com.example.foroom.pages.CreateChatPage

/**
 * Reusable chat creation and chat list flows and checks.
 */
object ChatSteps {

    fun checkHomeScreenDisplayed() {
        ChatsPage.waitForHomeScreen()
    }

    fun openCreateChat() {
        CreateChatPage.tapCreateChatNavigation()
        CreateChatPage.waitUntilDisplayed()
    }

    fun enterChatName(name: String) {
        CreateChatPage.typeChatName(name)
    }

    fun selectChatImage(index: Int) {
        CreateChatPage.waitForImagesLoaded()
        CreateChatPage.tapImage(index)
        CreateChatPage.checkSelectedImage(index)
    }

    fun tapCreateChat() {
        CreateChatPage.tapCreateChat()
    }

    fun createChat(name: String, imageIndex: Int) {
        openCreateChat()
        enterChatName(name)
        selectChatImage(imageIndex)
        tapCreateChat()
    }

    fun checkOpenedChat(name: String) {
        ConversationPage.waitForChatTitle(name)
    }

    fun closeChat() {
        ConversationPage.tapClose()
        ChatsPage.waitUntilDisplayed()
    }

    fun searchChat(name: String) {
        ChatsPage.typeSearch(name)
    }

    fun checkChatInList(name: String) {
        ChatsPage.waitForChatCard(name)
    }

    /** Finds the chat by its exact title in the chat list and opens it. */
    fun openChat(name: String) {
        ChatsPage.waitUntilDisplayed()
        searchChat(name)
        ChatsPage.tapOpenChat(name)
        checkOpenedChat(name)
    }

    /** Test data preparation: reuses the chat when it already exists on the device, otherwise creates it. */
    fun ensureChatExists(name: String, imageIndex: Int) {
        ChatsPage.waitUntilDisplayed()
        searchChat(name)
        if (ChatsPage.isChatCardDisplayed(name, CHAT_LOOKUP_TIMEOUT_SEC)) return

        createChat(name, imageIndex)
        checkOpenedChat(name)
        closeChat()
    }

    private const val CHAT_LOOKUP_TIMEOUT_SEC = 5L
}
