package com.example.ibanvalidator.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ibanvalidator.utils.Textview
import com.example.ibanvalidator.viewmodel.CurrencyViewModel

@Composable
fun Tabs(modifier: Modifier=Modifier,viewModel: CurrencyViewModel) {

    var tabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf("IBN Validator", "Converter")

    Column(modifier = modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Textview(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            0 -> IbnValidator(viewModel = viewModel)

            1 -> CurrencyList(viewModel=viewModel)
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TabsPrev() {
//Tabs()
}