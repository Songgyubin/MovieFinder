package com.gyub.feature.main

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.gyub.core.navigation.MainTabRoute
import com.gyub.feature.main.navigator.rememberMainNavigator
import com.gyub.ui_test_hilt_manifest.HiltComponentActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get: Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()
    private lateinit var testNavController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            testNavController = TestNavHostController(LocalContext.current)
            testNavController.navigatorProvider.addNavigator(ComposeNavigator())

            MainScreenContent(
                navigator = rememberMainNavigator(navController = testNavController),
            )
        }
    }

    @Test
    fun 바텀바에_메인_탭을_노출한다() {
        composeTestRule.onNodeWithTag(MainBottomBarTestTag)
            .assertIsDisplayed()
    }

    @Test
    fun 시작_화면은_홈이다() {
        val actual = testNavController.currentDestination?.hasRoute<MainTabRoute.HOME>()
        assertEquals(true, actual)
    }
}