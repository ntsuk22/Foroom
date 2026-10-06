package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.util.TreeIterables
import com.example.design_system.components.image_chooser.ImageChooserItemView
import com.example.design_system.components.image_chooser.ImageChooserListView
import com.example.foroom.Helper.waitUntilVisible
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.assertEquals
import java.util.concurrent.TimeoutException
import com.example.design_system.R as DesignR

/** Shared matchers and actions used by several pages. */

internal const val VIEW_TIMEOUT_SEC = 5L
internal const val SCREEN_TIMEOUT_SEC = 15L
internal const val RESPONSE_TIMEOUT_SEC = 15L
private const val POLL_INTERVAL_MS = 100L

/** The `inputEditText` inside the given custom `Input`. */
internal fun inputEditText(input: Matcher<View>): Matcher<View> =
    allOf(withId(DesignR.id.inputEditText), isDescendantOfA(input))

/** The `descriptionTextView` (error/info text) inside the given custom `Input`. */
internal fun inputDescription(input: Matcher<View>): Matcher<View> =
    allOf(withId(DesignR.id.descriptionTextView), isDescendantOfA(input))

internal fun typeInto(editText: Matcher<View>, text: String) {
    onView(editText).waitUntilVisible(VIEW_TIMEOUT_SEC).perform(replaceText(text), closeSoftKeyboard())
}

internal fun tapWhenVisible(view: Matcher<View>, timeoutSec: Long = VIEW_TIMEOUT_SEC) {
    onView(view).waitUntilVisible(timeoutSec).perform(click())
}

/**
 * Image choosers (avatars, chat emojis) load asynchronously and show blank
 * placeholders until the images arrive.
 */
internal fun waitForImagesLoaded(chooser: Matcher<View>, timeoutSec: Long = RESPONSE_TIMEOUT_SEC) {
    val endTime = System.currentTimeMillis() + timeoutSec * 1000
    while (true) {
        var loaded = false
        onView(chooser).check { view, noViewFoundException ->
            noViewFoundException?.let { throw it }
            val list = view as ImageChooserListView
            loaded = list.isChoosingEnabled && list.images.isNotEmpty() && list.images.none { it.url.isEmpty() }
        }
        if (loaded) return
        if (System.currentTimeMillis() > endTime) throw TimeoutException("Images were not loaded in $timeoutSec sec")
        Thread.sleep(POLL_INTERVAL_MS)
    }
}

internal fun tapImage(chooser: Matcher<View>, index: Int) {
    tapWhenVisible(imageAt(chooser, index))
}

internal fun checkSelectedImage(chooser: Matcher<View>, index: Int) {
    onView(chooser).check { view, noViewFoundException ->
        noViewFoundException?.let { throw it }
        assertEquals("Selected image index", index, (view as ImageChooserListView).selectedIndex)
    }
}

/**
 * Image item at [index] (row by row) inside [chooser]. Stateless, unlike the
 * Helper `withIndex`, so it can be matched again by a wait followed by an action.
 */
private fun imageAt(chooser: Matcher<View>, index: Int): Matcher<View> = allOf(
    isDescendantOfA(chooser),
    object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("image chooser item at index $index")
        }

        override fun matchesSafely(view: View): Boolean {
            if (view !is ImageChooserItemView) return false
            var parent = view.parent
            while (parent != null && parent !is ImageChooserListView) parent = parent.parent
            val list = parent as? ImageChooserListView ?: return false
            val items = TreeIterables.breadthFirstViewTraversal(list).filterIsInstance<ImageChooserItemView>()
            return items.indexOf(view) == index
        }
    }
)
