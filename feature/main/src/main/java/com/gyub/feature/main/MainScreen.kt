package com.gyub.feature.main

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.gyub.feature.main.component.MainBottomNavigationBar
import com.gyub.feature.main.component.MainNavHost
import com.gyub.feature.main.navigator.MainNavigator
import com.gyub.feature.main.navigator.MainTab
import com.gyub.feature.main.navigator.rememberMainNavigator
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import java.net.UnknownHostException

/**
 * 메인 화면
 *
 * @author   Gyub
 * @created  2024/08/06
 */
@Composable
fun MainScreen(navigator: MainNavigator = rememberMainNavigator()) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val localContextResource = LocalContext.current.resources
    val onShowErrorSnackBar: (throwable: Throwable?) -> Unit = { throwable ->
        coroutineScope.launch {
            snackBarHostState.currentSnackbarData?.dismiss()

            snackBarHostState.showSnackbar(
                when (throwable) {
                    is UnknownHostException -> localContextResource.getString(com.gyub.core.common.R.string.core_common_error_message_network)
                    else -> localContextResource.getString(com.gyub.core.common.R.string.core_common_error_message_unknown)
                }
            )
        }
    }

    MainScreenContent(
        navigator = navigator,
        onShowErrorSnackBar = onShowErrorSnackBar,
        snackBarHostState = snackBarHostState
    )
}

@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    Scaffold(
        modifier = modifier,
        content = { innerPadding ->
            MainNavHost(
                navigator = navigator,
                innerPadding = innerPadding,
                onShowErrorSnackBar = onShowErrorSnackBar,
            )
        },
        bottomBar = {
            MainBottomNavigationBar(
                modifier = Modifier.navigationBarsPadding(),
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