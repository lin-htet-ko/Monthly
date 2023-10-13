package com.linhtetko.monthly.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.linhtetko.monthly.R
import com.linhtetko.monthly.ui.theme.MonthlyTheme

@Composable
fun ComposableWithLabelAndAction(
    modifier: Modifier = Modifier,
    labelContent: @Composable () -> Unit,
    actionLabel: String? = null,
    onTapAction: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier.animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessMedium
            )
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            labelContent()
            if (actionLabel != null)
                TextButton(onClick = onTapAction) {
                    Text(text = actionLabel)
                }
        }
        Spacer2x()
        content()
    }
}

@Preview
@Composable
fun ComposableWithLabelAndActionPreview() {
    MonthlyTheme {
        ComposableWithLabelAndAction(
            labelContent = {
                Text(
                    text = stringResource(id = R.string.lbl_flatmates),
                    fontSize = MaterialTheme.typography.h6.fontSize
                )
            },
            actionLabel = stringResource(id = R.string.lbl_add_new),
            onTapAction = {},
            content = {}
        )
    }
}

@Composable
fun ComposableWithLabel(
    modifier: Modifier = Modifier,
    label: String,
    content: @Composable () -> Unit
) {
    ComposableWithLabelAndAction(
        modifier = modifier,
        labelContent = {
            Text(
                text = label,
                fontSize = MaterialTheme.typography.h6.fontSize
            )
        }, content = content
    )
}

@Preview
@Composable
fun ComposableWithLabelPreview() {
    MonthlyTheme {
        ComposableWithLabel(label = "Name") {}
    }
}

@Composable
fun HeaderText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colors.onSurface
) {
    Text(
        modifier = modifier,
        text = text,
        fontWeight = MaterialTheme.typography.h1.fontWeight,
        fontSize = MaterialTheme.typography.h4.fontSize,
        color = color
    )
}

@Preview
@Composable
fun HeaderTextPreview() {
    MonthlyTheme {
        HeaderText(text = "Header Title")
    }
}