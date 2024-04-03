package com.dhkim.lezhin

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.ComposeContentTestRule

@OptIn(ExperimentalTestApi::class)
fun ComposeContentTestRule.waitUntilExists(
    matcher: SemanticsMatcher,
    timeoutMillis: Long = 1000L
) = waitUntilNodeCount(matcher, 1, timeoutMillis)

@OptIn(ExperimentalTestApi::class)
fun ComposeContentTestRule.waitUntilNoExists(
    matcher: SemanticsMatcher,
    timeoutMillis: Long = 1000L
) = waitUntilNodeCount(matcher, 0, timeoutMillis)
