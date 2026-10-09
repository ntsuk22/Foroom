package com.example.foroom.steps

import com.example.foroom.pages.RegistrationPage

/**
 * Reusable registration flows and checks.
 */
object RegistrationSteps {

    fun checkRegistrationScreenDisplayed() {
        RegistrationPage.waitUntilDisplayed()
    }

    fun enterUserName(userName: String) {
        RegistrationPage.typeUserName(userName)
    }

    fun enterPassword(password: String) {
        RegistrationPage.typePassword(password)
    }

    fun enterRepeatPassword(password: String) {
        RegistrationPage.typeRepeatPassword(password)
    }

    fun selectAvatar(index: Int) {
        RegistrationPage.waitForAvatarsLoaded()
        RegistrationPage.tapAvatar(index)
        RegistrationPage.checkSelectedAvatar(index)
    }

    fun tapSignUp() {
        RegistrationPage.tapSignUp()
    }

    fun register(userName: String, password: String, avatarIndex: Int) {
        enterUserName(userName)
        enterPassword(password)
        enterRepeatPassword(password)
        selectAvatar(avatarIndex)
        tapSignUp()
    }

    fun checkHomeScreenDisplayed() {
        RegistrationPage.waitForHomeScreen()
    }
}
