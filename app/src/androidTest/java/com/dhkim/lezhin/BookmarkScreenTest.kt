package com.dhkim.lezhin

import androidx.activity.compose.setContent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.dhkim.lezhin.presentation.bookmark.BookmarkScreen
import com.dhkim.lezhin.presentation.main.MainActivity
import com.dhkim.lezhin.ui.theme.LezhinTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class BookmarkScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeTestRule.activity.setContent {
            LezhinTheme {
                BookmarkScreen()
            }
        }
    }

    @Test
    fun checkDisplayItems() {
        composeTestRule.waitUntilNoExists(
            hasTestTag("1"),
            1000L
        )
    }
}