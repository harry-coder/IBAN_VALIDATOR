package com.example.ibanvalidator.views

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.ibanvalidator.R
import com.example.ibanvalidator.utils.Textview
import com.example.ibanvalidator.utils.color
import com.example.ibanvalidator.utils.dp
import com.example.ibanvalidator.utils.sp


@Composable
fun CCAppBar(
    context: Context,
    modifier: Modifier = Modifier,
    title: String = "Add Notes",
    showMenu: Boolean = true,

    ) {

    TopAppBar(
        modifier = modifier.height(R.dimen._60sdp.dp()),
        backgroundColor = Color.White,
        elevation = R.dimen._2sdp.dp()
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(R.dimen._50sdp.dp())
                .padding(horizontal = R.dimen._20sdp.dp()),
            horizontalArrangement = Arrangement.spacedBy(R.dimen._8sdp.dp()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Textview(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f),
                text = title,
                fontSize = R.dimen._16ssp.sp(),
                color = R.color.sa_input_txt_color.color(),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CCAppBarPrev() {
    //  CCAppBar( context = LocalContext.current)
}
