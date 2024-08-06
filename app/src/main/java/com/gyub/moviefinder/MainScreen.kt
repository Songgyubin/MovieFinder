package com.gyub.moviefinder

import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.gyub.moviefinder.component.MainBottomNavigationBar
import com.gyub.moviefinder.component.MainNavHost
import com.gyub.moviefinder.navigator.MainNavigator
import com.gyub.moviefinder.navigator.MainTab
import com.gyub.moviefinder.navigator.rememberMainNavigator
import kotlinx.coroutines.launch
import java.net.UnknownHostException

/**
 *
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
                    is UnknownHostException -> localContextResource.getString(R.string.error_message_network)
                    else -> localContextResource.getString(R.string.error_message_unknown)
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
                selectedDestination = navigator.currentTab ?: MainTab.HOME,
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