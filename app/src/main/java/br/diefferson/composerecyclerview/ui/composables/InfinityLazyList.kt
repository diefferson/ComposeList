package br.diefferson.composerecyclerview.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> InfinityLazyList(
    items: State<List<T>>,
    isLastPage: State<Boolean>,
    onNextPage: () -> Unit,
    itemContent: @Composable (T) -> Unit
) {

    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        items(items.value) {
            itemContent(it)
        }

        if (!isLastPage.value && items.value.isNotEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomCenter,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(36.dp)

                    )
                }
            }
        }

        item {
            LaunchedEffect(key1 = items.value.size) {
                if (!isLastPage.value) {
                    onNextPage()
                }
            }
        }
    }
}