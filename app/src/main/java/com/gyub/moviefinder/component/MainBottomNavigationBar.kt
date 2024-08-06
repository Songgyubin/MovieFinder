package com.gyub.moviefinder.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gyub.moviefinder.navigator.MainTab
import com.gyub.moviefinder.design.theme.MovieFinderTheme

/**
 *
 *
 * @author   Gyub
 * @created  2024/08/06
 */
@Composable
fun MainBottomNavigationBar(
    selectedDestination: MainTab,
    navigateToTopLevelDestination: (MainTab) -> Unit,
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        contentColor = MaterialTheme.colorScheme.surface,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        MainTab.entries.forEach { tab ->
            NavigationBarItem(
                modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                colors = NavigationBarItemColors(
                    selectedIndicatorColor = MaterialTheme.colorScheme.surface,
                    selectedIconColor = MaterialTheme.colorScheme.surface,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.surface,
                    unselectedTextColor = MaterialTheme.colorScheme.inversePrimary,
                    disabledIconColor = MaterialTheme.colorScheme.surface,
                    disabledTextColor = MaterialTheme.colorScheme.surface,
                ),
                selected = selectedDestination == tab,
                onClick = { navigateToTopLevelDestination(tab) },
                label = {
                    Text(
                        text = stringResource(id = tab.titleTextId),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                icon = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainBottomNavigationBarPreview() {
    MovieFinderTheme {
        MainBottomNavigationBar(
            selectedDestination = MainTab.HOME,
            navigateToTopLevelDestination = {}
        )
    }
}