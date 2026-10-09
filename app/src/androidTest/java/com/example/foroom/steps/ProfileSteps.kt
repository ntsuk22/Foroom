package com.example.foroom.steps

import com.example.foroom.pages.ChangeLanguagePage
import com.example.foroom.pages.ChangePasswordPage
import com.example.foroom.pages.ProfilePage

/**
 * Reusable profile settings flows and checks.
 */
object ProfileSteps {

    fun openProfile() {
        ProfilePage.tapProfileNavigation()
        ProfilePage.waitUntilDisplayed()
    }

    fun openChangePassword() {
        ProfilePage.tapChangePassword()
        ChangePasswordPage.waitUntilDisplayed()
    }

    fun enterNewPassword(password: String) {
        ChangePasswordPage.typePassword(password)
        ChangePasswordPage.typeRepeatPassword(password)
    }

    fun confirmPasswordChange() {
        ChangePasswordPage.tapConfirm()
    }

    fun changePassword(newPassword: String) {
        openChangePassword()
        enterNewPassword(newPassword)
        confirmPasswordChange()
    }

    fun selectGeorgian() {
        openChangeLanguage()
        ChangeLanguagePage.tapGeorgian()
    }

    fun selectEnglish() {
        openChangeLanguage()
        ChangeLanguagePage.tapEnglish()
    }

    fun checkGeorgianLabelsDisplayed() {
        ProfilePage.waitForChangeLanguageLabel(GEORGIAN_CHANGE_LANGUAGE)
        ProfilePage.waitForSignOutLabel(GEORGIAN_SIGN_OUT)
    }

    fun checkEnglishLabelsDisplayed() {
        ProfilePage.waitForChangeLanguageLabel(ENGLISH_CHANGE_LANGUAGE)
        ProfilePage.waitForSignOutLabel(ENGLISH_SIGN_OUT)
    }

    fun signOut() {
        openProfile()
        ProfilePage.tapSignOut()
    }

    private fun openChangeLanguage() {
        ProfilePage.waitUntilDisplayed()
        ProfilePage.tapChangeLanguage()
        ChangeLanguagePage.waitUntilDisplayed()
    }

    private const val GEORGIAN_CHANGE_LANGUAGE = "ენის შეცვლა"
    private const val GEORGIAN_SIGN_OUT = "გამოსვლა"
    private const val ENGLISH_CHANGE_LANGUAGE = "Change Language"
    private const val ENGLISH_SIGN_OUT = "Sign Out"
}
