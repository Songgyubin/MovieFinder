package com.gyub.feature.main

import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import com.gyub.core.ui.ObserveAsEvents
import com.gyub.core.ui.SnackbarController
import com.gyub.core.ui.SnackbarEvent
import com.gyub.feature.main.component.MainBottomNavigationBar
import com.gyub.feature.main.component.MainNavHost
import com.gyub.feature.main.navigator.MainNavigator
import com.gyub.feature.main.navigator.MainTab
import com.gyub.feature.main.navigator.rememberMainNavigator
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.theme.BpkTheme

/**
 * 메인 화면
 *
 * @author   Gyub
 * @created  2024/08/06
 */

const val MainBottomBarTestTag = "MainBottomBarTestTag"

@Composable
fun MainScreen(navigator: MainNavigator = rememberMainNavigator()) {
    val localContextResource = LocalContext.current.resources
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    ObserveAsEvents(
        flow = SnackbarController.events,
        key1 = snackbarHostState
    ) { event ->
        coroutineScope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            val message = when (event) {
                is SnackbarEvent.Message -> event.message
                is SnackbarEvent.MessageId -> localContextResource.getString(event.messageId)
            }

            val result = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Short
            )

            if (result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    MainScreenContent(
        navigator = navigator,
        snackBarHostState = snackbarHostState
    )
}

@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    snackBarHostState: SnackbarHostState = SnackbarHostState(),
) {
    Scaffold(
        containerColor = BpkTheme.colors.surfaceDefault,
        content = { innerPadding ->
            MainNavHost(
                navigator = navigator,
                innerPadding = innerPadding,
            )
        },
        bottomBar = {
            MainBottomNavigationBar(
                modifier = Modifier
                    .testTag(MainBottomBarTestTag),
                tabs = MainTab.entries.toPersistentList(),
                selectedTab = navigator.currentTab ?: MainTab.HOME,
                isVisible = navigator.shouldShowBottomBar(),
                navigateToTopLevelDestination = {
                    navigator.navigate(it)
                }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier
                    .imePadding()
            )
        }
    )
}