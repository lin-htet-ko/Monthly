package com.linhtetko.monthly.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.linhtetko.monthly.R
import com.linhtetko.monthly.ui.theme.MonthlyTheme

@Composable
fun TextFieldWithLabel(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit
) {
    Column(modifier = modifier) {
        Text(text = label, fontSize = MaterialTheme.typography.body2.fontSize)
        Spacer1x()
        MonthlyTextField(
            keyboardOptions = keyboardOptions,
            value = value,
            onValueChange = onValueChange
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldWithLabelPreview() {
    var value by remember {
        mutableStateOf("")
    }
    MonthlyTheme {
        TextFieldWithLabel(
            modifier = Modifier.fillMaxWidth(),
            label = "Name",
            value = value,
            onValueChange = {
                value = it
            }
        )
    }
}

@Composable
fun MonthlyTextField(
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        keyboardOptions = keyboardOptions,
        modifier = modifier
            .fillMaxWidth(),
        value = value, onValueChange = onValueChange,
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            backgroundColor = Color.Gray.copy(0.1f)
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun MonthlyTextFieldPreview() {
    MonthlyTheme {
        MonthlyTextField(value = "", onValueChange = {})
    }
}