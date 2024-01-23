package com.example.ibanvalidator.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.flowWithLifecycle
import com.example.ibanvalidator.R
import com.example.ibanvalidator.intruders.events.UIEvents
import com.example.ibanvalidator.model.IbanData
import com.example.ibanvalidator.utils.AdvanceEditText
import com.example.ibanvalidator.utils.Textview
import com.example.ibanvalidator.utils.color
import com.example.ibanvalidator.utils.dp
import com.example.ibanvalidator.utils.sp
import com.example.ibanvalidator.viewmodel.CurrencyViewModel
import kotlinx.coroutines.flow.collectLatest


@Composable
fun IbnValidator(modifier: Modifier = Modifier, viewModel: CurrencyViewModel) {
    var descriptionEmptyStateError by remember {
        mutableStateOf(false)
    }
    var descriptionState by remember {
        mutableStateOf("")
    }

    var rememberIbanData by remember {
        mutableStateOf(IbanData())
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = Unit) {
        viewModel.dataFlow.flowWithLifecycle(lifecycleOwner.lifecycle).collectLatest { data ->
            when (data.showLoader) {
                true -> {}
                else -> {}
            }

            if(data.data.valid){
                rememberIbanData=data.data
            }


        }
    }

    Column(
        modifier
            .fillMaxSize()
            .padding(R.dimen._16sdp.dp()),
        verticalArrangement = Arrangement.Top,
    ) {
        AdvanceEditText(
            modifier = modifier
                .padding(top = R.dimen._20sdp.dp())
                .fillMaxWidth()
                .height(R.dimen._52sdp.dp())
                .background(Color.White, shape = RoundedCornerShape(10.dp)),
            value = descriptionState,
            placeholderText = stringResource(R.string.enter_iban),
            focusedColor = R.color.grey_line.color(),
            unFocusedColor = R.color.upi_text_grey.color(),
            onValueChange = {
                descriptionEmptyStateError = it.isEmpty()
                descriptionState = it
            },
            visualTransformation = VisualTransformation.None,
            isError = descriptionEmptyStateError,

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
            singleLine = false

        )
        if (descriptionEmptyStateError) {
            Textview(
                text = stringResource(R.string.please_enter_iban),
                color = MaterialTheme.colors.error,
                modifier = modifier.padding(start = 16.dp),
            )
        }

        ProceedButton(activeState = descriptionState.isEmpty()) {
            viewModel.onEvents(UIEvents.IbanValidate(descriptionState))
        }
        ShowBankData(data = rememberIbanData)

    }
}

@Composable
private fun ProceedButton(
    modifier: Modifier = Modifier,
    activeState: Boolean,
    onClick: () -> Unit
) {
    val rememberButtonsState by rememberUpdatedState(newValue = activeState)
    Button(
        modifier = modifier
            .height(R.dimen._70sdp.dp())
            .fillMaxWidth()
            .padding(
                start = R.dimen._20sdp.dp(),
                end = R.dimen._20sdp.dp(),
                bottom = R.dimen._5sdp.dp(),
                top = R.dimen._20sdp.dp()
            ),
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(size = R.dimen._10sdp.dp()),
        colors = ButtonDefaults.buttonColors(if (!rememberButtonsState) R.color.color_ff7744.color() else R.color.grey_line.color())
    )
    {
        Textview(
            text = stringResource(R.string.proceed),
            color = R.color.white.color(),
            fontSize = R.dimen._16ssp.sp(),
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ShowBankData(data: IbanData, modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .padding(R.dimen._16sdp.dp())
        .verticalScroll(rememberScrollState())
        .fillMaxSize()) {

        Textview(text = "Bank Code: ${data.bankData.bankCode}")
        Textview(text = "Bank Name: ${data.bankData.name}")
        Textview(text = "Bank City: ${data.bankData.city}")


    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun IbnValidatorPrev() {
    //IbnValidator()
}