package com.example.foroom.steps

import com.example.foroom.pages.LoginPage

/**
 * Reusable login flows and checks.
 */
object LoginSteps {

    fun checkLoginScreenDisplayed() {
        LoginPage.waitUntilDisplayed()
    }

    fun enterUserName(userName: String) {
        LoginPage.typeUserName(userName)
    }

    fun enterPassword(password: String) {
        LoginPage.typePassword(password)
    }

    fun tapLogIn() {
        LoginPage.tapLogIn()
    }

    fun logIn(userName: String, password: String) {
        enterUserName(userName)
        enterPassword(password)
        tapLogIn()
    }

    fun openRegistration() {
        LoginPage.tapSignUp()
    }

    fun checkPasswordErrorDisplayed() {
        LoginPage.waitForPasswordError()
    }

    fun checkUserNameErrorDisplayed() {
        LoginPage.waitForUserNameError()
    }

    fun checkUserNameErrorNotDisplayed() {
        LoginPage.checkUserNameErrorNotDisplayed()
    }

    /**
     * Test data preparation: signs in with the account, registering it first when it does not
     * exist on this device yet. Ends on the home screen.
     */
    fun logInOrRegister(userName: String, password: String, avatarIndex: Int) {
        checkLoginScreenDisplayed()
        logIn(userName, password)
        when (LoginPage.waitForLoginResult()) {
            LoginPage.LoginResult.HOME -> Unit
            LoginPage.LoginResult.UNKNOWN_USER -> {
                openRegistration()
                RegistrationSteps.checkRegistrationScreenDisplayed()
                RegistrationSteps.register(userName, password, avatarIndex)
                RegistrationSteps.checkHomeScreenDisplayed()
            }
            LoginPage.LoginResult.WRONG_PASSWORD ->
                throw AssertionError("Account '$userName' exists on this device with a different password")
        }
    }
}
