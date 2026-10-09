package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.steps.ChatSteps
import com.example.foroom.steps.ConversationSteps
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.ProfileSteps
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConversationTests {

    private val activityRule = ActivityScenarioRule(ForoomActivity::class.java)

    // Every test starts signed out on the login screen, whatever the previous run left behind
    @get:Rule
    val rules: RuleChain = RuleChain.outerRule(ClearUserSessionRule()).around(activityRule)

    /**
     * Makes sure both accounts and the three chats exist on this device, creating whatever is
     * missing. Runs once per test process and leaves the app signed out on the login screen.
     */
    @Before
    fun prepareTestData() {
        if (testDataPrepared) return

        LoginSteps.logInOrRegister(USER_B, PASSWORD_B, AVATAR_INDEX)
        signOut()

        LoginSteps.logInOrRegister(USER_A, PASSWORD_A, AVATAR_INDEX)
        ChatSteps.ensureChatExists(JOHN_WEEK_CHAT, CHAT_IMAGE_INDEX)
        ChatSteps.ensureChatExists(OWN_CHAT, CHAT_IMAGE_INDEX)
        ChatSteps.ensureChatExists(SHARED_CHAT, CHAT_IMAGE_INDEX)
        signOut()

        testDataPrepared = true
    }

    @Test
    fun sendDrinkInvitationInJohnWeekChatAndSeeItAfterReopening() {
        val invitation = "let's go for a drink ${uniqueSuffix()}"

        signIn(USER_A, PASSWORD_A)
        ChatSteps.openChat(JOHN_WEEK_CHAT)

        ConversationSteps.sendMessage(invitation)
        ConversationSteps.checkMessageDisplayed(invitation, USER_A)

        ChatSteps.closeChat()
        ChatSteps.openChat(JOHN_WEEK_CHAT)
        ConversationSteps.checkMessageDisplayed(invitation, USER_A)
    }

    @Test
    fun askFavouriteAutomationAcademyModuleInOwnChat() {
        val question = "Which module do you like most in the Automation Academy? ${uniqueSuffix()}"

        signIn(USER_A, PASSWORD_A)
        ChatSteps.openChat(OWN_CHAT)

        ConversationSteps.sendMessage(question)
        ConversationSteps.checkMessageDisplayed(question, USER_A)
    }

    @Test
    fun continueSharedConversationWithSecondAccount() {
        val suffix = uniqueSuffix()
        val greeting = "Hello from User A $suffix"
        val reply = "Hi User A, User B here $suffix"

        // User A greets and pushes the greeting into older history
        signIn(USER_A, PASSWORD_A)
        ChatSteps.openChat(SHARED_CHAT)
        ConversationSteps.sendMessage(greeting)
        ConversationSteps.checkMessageDisplayed(greeting, USER_A)
        ConversationSteps.sendMessages((1..ADDITIONAL_MESSAGE_COUNT).map { "Message $it $suffix" })
        ChatSteps.closeChat()
        signOut()

        // User B scrolls back to the greeting and replies
        signIn(USER_B, PASSWORD_B)
        ChatSteps.openChat(SHARED_CHAT)
        ConversationSteps.swipeToOlderMessage(greeting, USER_A)
        ConversationSteps.sendMessage(reply)
        ConversationSteps.checkMessageDisplayed(reply, USER_B)
        ChatSteps.closeChat()
        signOut()

        // User A sees the reply
        signIn(USER_A, PASSWORD_A)
        ChatSteps.openChat(SHARED_CHAT)
        ConversationSteps.checkMessageDisplayed(reply, USER_B)
    }

    private fun signIn(userName: String, password: String) {
        LoginSteps.checkLoginScreenDisplayed()
        LoginSteps.logIn(userName, password)
        ChatSteps.checkHomeScreenDisplayed()
    }

    private fun signOut() {
        ProfileSteps.signOut()
        LoginSteps.checkLoginScreenDisplayed()
    }

    companion object {
        private const val USER_A = "nino_user_a"
        private const val PASSWORD_A = "UserA12345!"
        private const val USER_B = "nino_user_b"
        private const val PASSWORD_B = "UserB12345!"

        private const val JOHN_WEEK_CHAT = "johnWeek"
        private const val OWN_CHAT = "Nino Tsukhishvili"
        private const val SHARED_CHAT = "something"

        private const val ADDITIONAL_MESSAGE_COUNT = 25
        private const val AVATAR_INDEX = 1
        private const val CHAT_IMAGE_INDEX = 1

        private var testDataPrepared = false

        private fun uniqueSuffix() = System.currentTimeMillis().toString()
    }
}
