package com.example.foroom.tests

import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import kotlinx.coroutines.runBlocking
import org.junit.rules.ExternalResource
import org.koin.core.context.GlobalContext

/**
 * Clears the saved session before the activity is launched, so every test starts
 * on the login screen regardless of which test ran before it.
 */
class ClearUserSessionRule : ExternalResource() {
    override fun before() {
        runBlocking { GlobalContext.get().get<ForoomUserDataStore>().clearUserData() }
    }
}
