package com.example.foroom.steps

import com.example.foroom.pages.ChatsPage
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
        ChatsPage.waitForOpenedChat(name)
    }

    fun closeChat() {
        ChatsPage.tapCloseChat()
        ChatsPage.waitUntilDisplayed()
    }

    fun searchChat(name: String) {
        ChatsPage.typeSearch(name)
    }

    fun checkChatInList(name: String) {
        ChatsPage.waitForChatCard(name)
    }
}
