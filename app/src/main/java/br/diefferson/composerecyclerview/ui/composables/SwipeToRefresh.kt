package br.diefferson.composerecyclerview.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeToRefresh(
    refreshingState: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    val state = rememberPullRefreshState(refreshingState, onRefresh)
    Box(Modifier.pullRefresh(state)) {
        content()
        PullRefreshIndicator(refreshingState, state, Modifier.align(Alignment.TopCenter))
    }
}