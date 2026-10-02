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
}
