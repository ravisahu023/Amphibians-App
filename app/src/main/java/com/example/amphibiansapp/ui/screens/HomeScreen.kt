package com.example.amphibiansapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibiansapp.R
import com.example.amphibiansapp.model.Ambhibian
import com.example.amphibiansapp.ui.AmphibianUiState
import com.example.amphibiansapp.ui.theme.AmphibiansAppTheme


@Composable
fun HomeScreen(
    amphibianUiState: AmphibianUiState, retryAction: () -> Unit, modifier: Modifier = Modifier
) {
    when (amphibianUiState) {
        is AmphibianUiState.Loading -> LoadingScreen(modifier)
        is AmphibianUiState.Success -> AmbhibiansList(
            ambhibian = amphibianUiState.amphibians, modifier
        )
        else  -> ErrorScreen(retryAction = retryAction, modifier)
    }
}

@Composable
fun AmphibianCard(ambhibian: Ambhibian, modifier: Modifier = Modifier) {

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(1f), elevation = 8.dp
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = ambhibian.name+"("+ambhibian.type+")", fontWeight = FontWeight.Bold, fontSize = 18.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 5.dp)
            )
            Text(
                text = ambhibian.description, fontWeight = FontWeight.Normal,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(10.dp)
            )

            AsyncImage(
                model = ImageRequest.Builder(
                    context = LocalContext.current
                ).data(ambhibian.imgSrc).build(),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                error = painterResource(id = android.R.drawable.stat_notify_error),
                placeholder = painterResource(id = R.drawable.loading_img),
                modifier = Modifier.fillMaxWidth()

            )
        }
    }
}

@Composable
fun AmbhibiansList(ambhibian: List<Ambhibian>, modifier: Modifier = Modifier) {

    LazyColumn(
        modifier = Modifier.padding(2.dp)
    ) {
        items(ambhibian) {
            AmphibianCard(ambhibian = it)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AmbhibianCardPreview() {
    AmphibiansAppTheme {
        val mockData = List(10) {
            Ambhibian(
                "$it", "eeeeeeeeeeeeeeeeeeeeeeeeeeee", "yyyyyyyyyyyyyyyyyyyyyyyyyy", ""
            )
        }
        AmbhibiansList(ambhibian = mockData)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.loading_failed))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}