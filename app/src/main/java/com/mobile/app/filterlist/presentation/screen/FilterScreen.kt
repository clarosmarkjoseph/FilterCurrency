package com.mobile.app.filterlist.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Preview(showBackground = true)
@Composable
fun FilterScreen(viewModel: FilterScreenViewModel = hiltViewModel()) {

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                title = { Text(text = "List of Currencies") },
                contentColor = Color.White
            )
        }
    ) {
        Column {
            val state = viewModel.stateFlow.observeAsState()
            when (val r = state.value) {
                is FilterState.Loading -> {
                    Log.e("showProgress: ", r.showProgress.toString())
                    ShowProgress(r.showProgress)
                }
                is FilterState.Success -> {
                    SearchBar(viewModel)
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        items(
                            items = r.list,
                            itemContent = { item ->
                                CurrencyItem(item = item)
                            }
                        )
                    }
                }
                is FilterState.Error -> {
                    ShowErrorFetchData(errorMessage = r.errorMessage, viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun CurrencyItem(item: String) {
    Card(
        elevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = item,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
            )
        }
    }
}

@Composable
fun SearchBar(viewModel: FilterScreenViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary
    ) {
        val textVal = viewModel.filterState.observeAsState().value ?: ""
        TextField(
            value = textVal,
            placeholder = {
                Text(
                    text = "Search here..",
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    color = Color.White
                )
            },
            textStyle = TextStyle(fontSize = MaterialTheme.typography.subtitle1.fontSize),
            onValueChange = {
                viewModel.onFilterCurrency(it)
            },
            singleLine = true
        )
    }
}

@Composable
fun ShowErrorFetchData(
    errorMessage: String,
    viewModel: FilterScreenViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = errorMessage,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(10.dp),
            onClick = { viewModel.loadData() }) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                textAlign = TextAlign.Center,
                text = "Update"
            )
        }
    }
}

@Composable
fun ShowProgress(shouldShow: Boolean) {
    if (shouldShow) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary
            )
        }
    }
}