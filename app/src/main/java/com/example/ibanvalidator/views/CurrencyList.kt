package com.example.ibanvalidator.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.flowWithLifecycle
import com.example.ibanvalidator.R
import com.example.ibanvalidator.intruders.events.UIEvents
import com.example.ibanvalidator.utils.Textview
import com.example.ibanvalidator.utils.color
import com.example.ibanvalidator.utils.dp
import com.example.ibanvalidator.viewmodel.CurrencyViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CurrencyList(viewModel: CurrencyViewModel,modifier: Modifier=Modifier) {
    val lifecycleOwner = LocalLifecycleOwner.current


    var currencyMap by remember {
        mutableStateOf(mutableMapOf<String, String>())
    }
    var expanded by remember {
        mutableStateOf(false)
    }
    val lambdaRemember = remember<(Boolean)->Unit> {
        {
            expanded=it
        }
    }
    val lambdaRemember2 = remember<(String)->Unit> {
        {
          viewModel. selectedItem.value=it
        }
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.currencyFlow.flowWithLifecycle(lifecycleOwner.lifecycle).collectLatest {
            currencyMap = it.data.data.toMutableMap()
        }
    }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(R.dimen._10sdp.dp()), verticalArrangement = Arrangement.Top ) {
        LazyRow {
            items(currencyMap.keys.toList()) {
                CurrencyItem(it,currencyMap[it]?:"",selectedItemTop=viewModel.selectedItem.value)
            }
        }

        Box {
            ShowSpinner(
                listItems = currencyMap.keys.toTypedArray(),
                expanded = expanded,
                stateExpanded = lambdaRemember,
                selectedItemState = lambdaRemember2,
                selectedItemTop = viewModel.selectedItem.value
            ){
                viewModel.onEvents(UIEvents.ConvertCurrency(it))
            }
        }
    }


}
@OptIn(ExperimentalMaterialApi::class)
@Composable
@Stable
private fun ShowSpinner(
    modifier: Modifier = Modifier,
    listItems: Array<String>,
    expanded: Boolean,
    stateExpanded: (Boolean) -> Unit,
    selectedItemState: (String) -> Unit,
    selectedItemTop: String,
    onClick:(item:String)->Unit
) {

    var selectedItem by remember {
        mutableStateOf("Currency: $selectedItemTop")
    }
    ExposedDropdownMenuBox(
        modifier = modifier.padding(top = 20.dp),
        expanded = expanded,
        onExpandedChange = { stateExpanded(!expanded) }) {

        Box(
            modifier = modifier
                .height(50.dp)
                .fillMaxWidth()
                .clickable {

                }
                .border(
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, R.color.upi_text_grey.color())
                )
                .background(Color.White, shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.CenterStart
        ) {
            Textview(
                modifier = modifier.padding(start = 10.dp),
                text = selectedItem,
                color = Color.LightGray
            )
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { stateExpanded(false) }
        ) {
            listItems.forEach { selectedOption ->
                // menu item
                DropdownMenuItem(onClick = {
                    selectedItem = "Currency: $selectedOption"
                    selectedItemState(selectedOption)
                    stateExpanded(false)
                    onClick(selectedOption)

                }) {
                    Text(text = selectedOption)
                }
            }
        }
    }

}
@Composable
private fun CurrencyItem(name: String, value: String, modifier: Modifier = Modifier,selectedItemTop: String) {

    Column(
        modifier
            .padding(5.dp)
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(5.dp))
            .padding(5.dp)
            .size(100.dp)
            .padding(5.dp), verticalArrangement = Arrangement.Center
    ) {
        Textview(text = "${if(selectedItemTop.isEmpty())value?.take(4)?.toDouble()?.times(3.25) else value.take(4)} $name",modifier=modifier.fillMaxWidth(),textAlign = TextAlign.Center)
        Textview(text = "IN", textAlign = TextAlign.Center,modifier=modifier.fillMaxWidth())
        Textview(text ="1 ${ selectedItemTop.takeIf { it.isNotEmpty() }?:run{"KWD"}}",textAlign=TextAlign.Center,modifier=modifier.fillMaxWidth())



    }

}