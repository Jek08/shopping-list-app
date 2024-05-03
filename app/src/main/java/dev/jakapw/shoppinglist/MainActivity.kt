package dev.jakapw.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dev.jakapw.shoppinglist.data.Product
import dev.jakapw.shoppinglist.ui.theme.ShoppingListTheme
import dev.jakapw.shoppinglist.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val productViewModel: ProductViewModel by viewModels()
    private lateinit var productList: List<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                productViewModel.productList.collect() {
                    productList = it
                }
            }
        }

        setContent {
            ShoppingListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShoppingListApp(
                        productList
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListApp(
    productList: List<Product>
) {
    Scaffold(
        topBar = {
                 CenterAlignedTopAppBar(
                     title = {
                         Text(
                             "Shopping List",
                             fontSize = 24.sp
                         )
                     },
                     colors = topAppBarColors(
                         containerColor = MaterialTheme.colorScheme.primaryContainer,
                         titleContentColor = MaterialTheme.colorScheme.primary
                     )
                 )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Product")
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            ShoppingList(productList, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun ShoppingList(
    productList: List<Product>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement
            .spacedBy(dimensionResource(id = R.dimen.medium_space)),
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.medium_padding))
    ) {
        items(productList) { product ->
            ElevatedCard(
                shape = MaterialTheme.shapes.extraSmall,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(start = 8.dp, end = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier
                            .size(68.dp)
                            .padding(8.dp)
                    )
                    ProductInformation(product = product)
                    Spacer(modifier = Modifier.weight(1f))
                    FilledIconToggleButton(
                        checked = false,
                        onCheckedChange = {}
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Check Product"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductInformation(
    product: Product,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement
            .spacedBy(dimensionResource(id = R.dimen.medium_space)),
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.medium_padding))
    ) {
        Text(
            text = product.productName,
            fontSize = 20.sp
        )
        Text(
            text = "${product.price}",
            fontSize = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListAppPreview() {
    val productList: List<Product> = listOf(
        Product("Sabun Cair", 30000)
    )
    ShoppingListApp(productList)
}