package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.RegistrationSteps
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExternalResource
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext

@RunWith(AndroidJUnit4::class)
class LoginAndRegistrationTests {

    private val activityRule = ActivityScenarioRule(ForoomActivity::class.java)

    // Clear the saved session before launching, so every test starts on the login screen
    // regardless of which test ran before it (registration logs the user in).
    @get:Rule
    val rules: RuleChain = RuleChain.outerRule(ClearUserSessionRule()).around(activityRule)

    @Test
    fun logInWithValidUserNameAndInvalidPasswordShowsPasswordError() {
        LoginSteps.checkLoginScreenDisplayed()
        LoginSteps.logIn(VALID_USERNAME, INVALID_PASSWORD)
        LoginSteps.checkPasswordErrorDisplayed()
        LoginSteps.checkUserNameErrorNotDisplayed()
    }

    @Test
    fun logInWithInvalidUserNameAndInvalidPasswordShowsBothErrors() {
        LoginSteps.checkLoginScreenDisplayed()
        LoginSteps.logIn(uniqueUserName(), INVALID_PASSWORD)
        LoginSteps.checkUserNameErrorDisplayed()
        LoginSteps.checkPasswordErrorDisplayed()
    }

    @Test
    fun registerWithUniqueUserNameOpensHomeScreen() {
        LoginSteps.checkLoginScreenDisplayed()
        LoginSteps.openRegistration()
        RegistrationSteps.checkRegistrationScreenDisplayed()
        RegistrationSteps.register(uniqueUserName(), VALID_PASSWORD, AVATAR_INDEX)
        RegistrationSteps.checkHomeScreenDisplayed()
    }

    private class ClearUserSessionRule : ExternalResource() {
        override fun before() {
            runBlocking { GlobalContext.get().get<ForoomUserDataStore>().clearUserData() }
        }
    }

    companion object {
        // Exists by default in the training build. For the server-connected build,
        // replace it with a test user created manually beforehand.
        private const val VALID_USERNAME = "student"
        private const val INVALID_PASSWORD = "WrongPassword1!"
        private const val VALID_PASSWORD = "Password123!"

        // Index 0 is preselected, so pick another avatar to exercise the selection
        private const val AVATAR_INDEX = 1

        private fun uniqueUserName() = "user_${System.currentTimeMillis()}"
    }
}
