package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.steps.ChatSteps
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.ProfileSteps
import com.example.foroom.steps.RegistrationSteps
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileAndChatTests {

    private val activityRule = ActivityScenarioRule(ForoomActivity::class.java)

    @get:Rule
    val rules: RuleChain = RuleChain.outerRule(ClearUserSessionRule()).around(activityRule)

    private lateinit var userName: String

    /**
     * Each test gets its own freshly registered account, so the password test can be
     * repeated and no test depends on another one. The account is signed out again,
     * leaving the app on the login screen.
     */
    @Before
    fun createTestAccount() {
        userName = "tester_${System.currentTimeMillis()}"

        LoginSteps.checkLoginScreenDisplayed()
        LoginSteps.openRegistration()
        RegistrationSteps.checkRegistrationScreenDisplayed()
        RegistrationSteps.register(userName, CURRENT_PASSWORD, AVATAR_INDEX)
        RegistrationSteps.checkHomeScreenDisplayed()
        ProfileSteps.signOut()
    }

    @Test
    fun changePasswordAndLogInWithNewPassword() {
        logIn(CURRENT_PASSWORD)

        ProfileSteps.openProfile()
        ProfileSteps.changePassword(NEW_PASSWORD)
        LoginSteps.checkLoginScreenDisplayed()

        logIn(NEW_PASSWORD)
    }

    @Test
    fun changeLanguageFromGeorgianToEnglishAndBack() {
        logIn(CURRENT_PASSWORD)
        ProfileSteps.openProfile()

        ProfileSteps.selectGeorgian()
        ProfileSteps.checkGeorgianLabelsDisplayed()

        ProfileSteps.selectEnglish()
        ProfileSteps.checkEnglishLabelsDisplayed()

        ProfileSteps.selectGeorgian()
        ProfileSteps.checkGeorgianLabelsDisplayed()
    }

    @Test
    fun createChatAndFindItInChatList() {
        val chatName = "$FULL_NAME ${System.currentTimeMillis()}"
        logIn(CURRENT_PASSWORD)

        ChatSteps.createChat(chatName, CHAT_IMAGE_INDEX)
        ChatSteps.checkOpenedChat(chatName)

        ChatSteps.closeChat()
        ChatSteps.searchChat(chatName)
        ChatSteps.checkChatInList(chatName)
    }

    private fun logIn(password: String) {
        LoginSteps.checkLoginScreenDisplayed()
        LoginSteps.logIn(userName, password)
        ChatSteps.checkHomeScreenDisplayed()
    }

    companion object {
        private const val FULL_NAME = "Nino Tsukhishvili"
        private const val CURRENT_PASSWORD = "Password123!"
        private const val NEW_PASSWORD = "NewPassword456!"

        // Index 0 is preselected, so pick another image to exercise the selection
        private const val AVATAR_INDEX = 1
        private const val CHAT_IMAGE_INDEX = 1
    }
}
