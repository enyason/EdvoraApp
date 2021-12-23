package com.enyason.edvoraapp.presentation.productList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberImagePainter
import com.enyason.edvoraapp.R
import com.enyason.edvoraapp.core.domain.Category
import com.enyason.edvoraapp.core.domain.ProductInformation
import androidx.compose.ui.text.style.TextOverflow

import com.enyason.edvoraapp.presentation.utils.toFormattedDate

@Composable
fun MainScreen(viewModel: ProductsViewModel) {

    val loading by viewModel.loading.observeAsState(initial = true)

    val viewState = viewModel.viewState.observeAsState()

    if (loading) {
        Box(
            Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.black292929))
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        when (val state = viewState.value) {
            ProductsViewModel.ViewState.Error -> ErrorOccurred("Some Error Occurred")
            ProductsViewModel.ViewState.NoProductsFound -> NothingToSeeHere()
            is ProductsViewModel.ViewState.ProductCategories -> ProductCategories(state.productCategories)
        }
    }
}

@Composable
fun NothingToSeeHere() {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.black292929))
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(R.string.no_products_found),
            color = Color.White
        )
    }
}

@Composable
fun ErrorOccurred(errorMsg: String) {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.black292929))
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = errorMsg,
            color = Color.White
        )
    }
}

@Composable
fun ProductCategories(categories: List<Category>) {

    val openDialog = remember { mutableStateOf(false) }

    if (openDialog.value) {
        FilterDialog { openDialog.value = false }
    }

    LazyColumn(
        content = {

            item {
                Filter(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 30.dp,
                            top = 28.dp,
                            bottom = 22.dp
                        ),
                    onclick = {
                        openDialog.value = true
                    }
                )
            }
            items(count = categories.lastIndex,
                itemContent = { index ->
                    CategorySection(
                        name = categories[index].name,
                        products = categories[index].products,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                })
        },
        modifier = Modifier
            .background(color = colorResource(id = R.color.black292929))
            .fillMaxSize()
    )
}

@Composable
fun Filter(modifier: Modifier, onclick: () -> Unit) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Box(
            modifier = Modifier
                .width(168.dp)
                .height(38.dp)
                .background(color = colorResource(id = R.color.black232323))
                .padding(start = 30.dp)
                .clip(RoundedCornerShape(5.dp))
                .clickable(onClick = onclick)
        ) {

            Text(
                text = stringResource(R.string.filters),
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 14.dp)
            )

            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 12.dp),
                tint = colorResource(id = R.color.gray7A7A7A)
            )
        }

        Box(
            modifier = Modifier
                .height(30.dp)
                .width(84.dp)
                .padding(end = 10.dp)
                .background(color = colorResource(id = R.color.black232323))
                .clip(RoundedCornerShape(5.dp))
        ) {
            Text(
                text = stringResource(R.string.clear_filter), color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(all = 4.dp)
            )
        }

    }
}

@Composable
fun CategorySection(name: String, products: List<ProductInformation>, modifier: Modifier) {

    Column(modifier = modifier) {

        Text(
            text = name,
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
            color = Color.White,
            modifier = Modifier.padding(start = 30.dp)
        )

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, top = 12.dp)
                .height(1.dp),
            color = colorResource(id = R.color.gray7A7A7A)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {

            LazyRow(
                content = {
                    items(
                        count = products.size,
                        itemContent = { index ->
                            val product = products[index]
                            val startPadding = if (index == 0) 24.dp else 10.dp
                            val endPadding = if (index == products.lastIndex) 24.dp else 10.dp
                            ProductItem(
                                product = product,
                                modifier = Modifier
                                    .width(210.dp)
                                    .height(150.dp)
                                    .padding(start = startPadding, end = endPadding)
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(color = Color.Black)
                            )
                        }
                    )
                },
                verticalAlignment = Alignment.CenterVertically,
            )

        }

    }
}

@Composable
fun ProductItem(modifier: Modifier = Modifier, product: ProductInformation) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {

        Row {
            Image(
                painter = rememberImagePainter(product.image),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .padding(start = 12.dp, top = 10.dp)
                    .clip(RoundedCornerShape(5.dp))
            )

            Column(
                modifier = Modifier
                    .size(70.dp)
                    .padding(
                        start = 20.dp,
                        top = 12.dp,
                        end = 12.dp
                    ),
                verticalArrangement = Arrangement.SpaceAround

            ) {
                Text(
                    text = product.productName,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = product.brandName,
                    color = Color.White.copy(alpha = 0.6f),
                    modifier = Modifier.padding(start = 2.dp, top = 10.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(id = R.string.product_price, product.price),
                    color = Color.White.copy(alpha = 0.6f),
                    modifier = Modifier.padding(top = 10.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis

                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 12.dp, end = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = product.address.city, color = Color.White.copy(alpha = 0.6f),
            )
            Text(
                text = "Date: ${product.date.toFormattedDate()}",
                color = Color.White.copy(alpha = 0.6f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Text(
            text = product.description, color = Color.White.copy(alpha = 0.6f),
            modifier = Modifier.padding(start = 12.dp, top = 14.dp, bottom = 14.dp)
        )
    }
}

@Composable
fun FilterDialog(onDismissRequest: () -> Unit) {

    Dialog(onDismissRequest = onDismissRequest) {

        Column(
            modifier = Modifier
                .width(228.dp)
                .height(282.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(color = colorResource(id = R.color.black131313))
        ) {
            Text(
                text = stringResource(id = R.string.filters),
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 28.dp, top = 30.dp)
            )

            Divider(
                modifier = Modifier.padding(
                    top = 12.dp,
                    start = 28.dp,
                    end = 42.dp,
                    bottom = 36.dp
                ),
                color = colorResource(id = R.color.grayCBCBCB)
            )

            listOf(
                "Products",
                "State",
                "City"
            ).forEach {
                FilterItem(it) {
                    // TODO show drop down
                }
            }
        }

    }
}

@Composable
fun FilterItem(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(38.dp)
            .padding(start = 24.dp, bottom = 12.dp, end = 36.dp)
            .background(color = colorResource(id = R.color.black232323))
            .clip(RoundedCornerShape(5.dp))
            .clickable(onClick = onClick)
    ) {

        Text(
            text = text,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 14.dp)
        )

        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp),
            tint = colorResource(id = R.color.gray7A7A7A)
        )
    }
}