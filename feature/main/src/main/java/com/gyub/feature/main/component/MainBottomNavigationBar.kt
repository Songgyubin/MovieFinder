package com.gyub.feature.main.component

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.gyub.feature.main.navigator.MainTab
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme

/**
 * 메인 화면 네비게이션 바
 *
 * @author   Gyub
 * @created  2024/08/06
 */
@Composable
fun MainBottomNavigationBar(
    modifier: Modifier = Modifier,
    selectedTab: MainTab,
    tabs: PersistentList<MainTab>,
    isVisible: Boolean,
    navigateToTopLevelDestination: (MainTab) -> Unit,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideIn { IntOffset(0, it.height) },
        exit = fadeOut() + slideOut { IntOffset(0, it.height) }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(color = BpkTheme.colors.surfaceDefault)
                .padding(horizontal = 28.dp),
        ) {
            tabs.forEach { tab ->
                MainBottomBarItem(tab = tab,
                    selected = selectedTab == tab,
                    onClick = { navigateToTopLevelDestination(tab) }
                )
            }
        }
    }
}

@Composable
private fun RowScope.MainBottomBarItem(
    modifier: Modifier = Modifier,
    tab: MainTab,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .weight(1f)
            .fillMaxHeight()
            .background(BpkTheme.colors.surfaceDefault)
            .selectable(
                selected = selected,
                indication = null,
                role = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        BpkText(
            text = stringResource(id = tab.titleTextId),
            color = if (selected) BpkTheme.colors.coreAccent else BpkTheme.colors.textSecondary,
            style = BpkTheme.typography.heading4
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainBottomNavigationBarPreview() {
    BpkTheme {
        MainBottomNavigationBar(
            selectedTab = MainTab.HOME,
            tabs = MainTab.entries.toPersistentList(),
            navigateToTopLevelDestination = {},
            isVisible = true
        )
    }
}