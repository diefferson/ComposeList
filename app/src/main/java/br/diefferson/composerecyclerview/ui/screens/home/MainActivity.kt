package br.diefferson.composerecyclerview.ui.screens.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import br.diefferson.composerecyclerview.models.Product
import br.diefferson.composerecyclerview.ui.composables.InfinityLazyList
import br.diefferson.composerecyclerview.ui.composables.SwipeToRefresh
import br.diefferson.composerecyclerview.ui.theme.ComposeRecyclerViewTheme
import coil.compose.rememberImagePainter


class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadProducts()
        setContent {
            ComposeRecyclerViewTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProductScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun ProductScreen(viewModel: MainViewModel) {
    val products = viewModel.products.collectAsState()
    val refreshing by viewModel.isRefreshing.collectAsState()

    SwipeToRefresh(refreshing, viewModel::refresh) {
        ProductList(viewModel, products)
    }
}

@Composable
fun ProductList(viewModel: MainViewModel, products: State<List<Product>>) {
    InfinityLazyList(
        items = products,
        isLastPage = viewModel.isLastPage.collectAsState(),
        onNextPage = viewModel::nexPage
    ) { product ->
        ProductItem(product)
    }
}

@Composable
fun ProductItem(product: Product) {
    val image = rememberImagePainter(data = product.images.first())

    Card(
        modifier = Modifier.padding(8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = image,
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text(text = product.title, style = MaterialTheme.typography.titleLarge)
                Text(text = product.description, style = MaterialTheme.typography.bodySmall)
                Text(text = "Price: \$${product.price}", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}