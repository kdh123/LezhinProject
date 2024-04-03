package com.dhkim.lezhin

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import com.dhkim.lezhin.presentation.main.MainActivity
import com.dhkim.lezhin.presentation.search.SearchScreen
import com.dhkim.lezhin.ui.theme.LezhinTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SearchScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeTestRule.activity.setContent {
            LezhinTheme {
                SearchScreen()
            }
        }
    }

    @Test
    fun searchScreenTest()  {
        val resultText = "대한민국"

        composeTestRule.onNodeWithTag("testSearchInput").performTextInput(resultText)
        composeTestRule.onNodeWithTag("testSearchInput").assert(hasText(resultText))
        composeTestRule.onNodeWithText("검색").assertIsDisplayed()
    }

    @Test
    fun inputSearchTextField() {
        val resultText = "대한민국"

        composeTestRule.onNodeWithTag("testSearchInput").performTextInput(resultText)
        composeTestRule.waitUntilExists(
            hasTestTag("5"),
            5000L
        )
    }
}