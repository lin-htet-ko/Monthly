package com.linhtetko.monthly.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linhtetko.monthly.R
import com.linhtetko.monthly.data.model.ItemModel
import com.linhtetko.monthly.ui.theme.ColorItem
import com.linhtetko.monthly.ui.theme.MonthlyTheme
import com.linhtetko.monthly.utils.DateManager

@Composable
fun ColorListItem(
    modifier: Modifier = Modifier,
    item: ColorItem,
    isSelected: Boolean,
    onSelect: (ColorItem) -> Unit
) {
    val strokeWidth = if (isSelected) R.dimen.size_general_stroke else R.dimen.size_empty
    val strokeColor = if (isSelected) MaterialTheme.colors.onPrimary else Color.Transparent
    val shape = RoundedCornerShape(dimensionResource(id = R.dimen.spacing_general))
    Box(
        modifier = modifier
            .size(dimensionResource(id = R.dimen.size_color_item))
            .clip(shape)
            .background(color = item.color)
            .border(
                width = dimensionResource(id = strokeWidth),
                color = strokeColor,
                shape = shape
            )
            .clickable { onSelect(item) }
    )
}

@Preview(showBackground = true)
@Composable
fun ColorListItemPreview() {
    MonthlyTheme {
        ColorListItem(
            item = ColorItem.colors.first(),
            isSelected = true,
            onSelect = {}
        )
    }
}

@Composable
fun FlatmateItem(modifier: Modifier = Modifier, image: String, name: String, onTap: () -> Unit) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onTap() }
            .padding(
                end = dimensionResource(id = R.dimen.spacing_2x),
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NetworkImage(
            data = image,
            modifier = Modifier
                .size(60.dp)
                .padding(dimensionResource(id = R.dimen.spacing_general))
                .clip(CircleShape)
        )
        Text(text = name)
        Spacer2x()
    }
}

@Preview
@Composable
private fun FlatmateItemPreview() {
    MonthlyTheme {
        FlatmateItem(
            name = "Lin Htet Ko",
            image = "",
            onTap = {}
        )
    }
}

@Composable
fun BoughtItem(modifier: Modifier = Modifier, item: ItemModel) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colors.primary.copy(alpha = 0.1f))
            .height(dimensionResource(id = R.dimen.size_bought_item))
            .padding(dimensionResource(id = R.dimen.spacing_2x))
    ) {
        Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
            ) {
                Text(
                    text = item.label ?: "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = item.price?.toString()?.ifEmpty { "--" } + " KS",
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                )
            }

            Image(
                modifier = Modifier.size(50.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.vector_bought_item),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

        }
        ListItem(
            modifier = Modifier
                .align(Alignment.End),
            label = item.userName ?: "",
            icon = Icons.Default.Person
        )
        Spacer1x()
        ListItem(
            modifier = Modifier.align(Alignment.End),
            label = DateManager.toPattern(item.createAt) ?: "--",
            icon = Icons.Default.DateRange
        )
    }

}

@Composable
fun ListItem(modifier: Modifier = Modifier, icon: ImageVector, label: String) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.spacing_2x)),
            text = label,
            fontWeight = FontWeight.Light,
            fontSize = MaterialTheme.typography.caption.fontSize
        )
        HorizontalSpacer1x()
        Icon(imageVector = icon, contentDescription = label, tint = MaterialTheme.colors.primary)
    }
}

@Preview(showBackground = true)
@Composable
fun BoughtItemPreview() {
    MonthlyTheme {
        val item = ItemModel(
            label = "တံမြက်စည်း",
            price = 3000, createAt = System.currentTimeMillis()
        )
        BoughtItem(item = item)
    }
}

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    text: String,
    onTap: () -> Unit,
    endContent: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.spacing_2x))
            .clickable { onTap() }
            .padding(dimensionResource(id = R.dimen.spacing_2x)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = MaterialTheme.typography.h6.fontSize
        )
        endContent()
    }
}

@Preview
@Composable
fun SettingItemPreview() {
    MonthlyTheme {
        SettingItem(
            text = "Language",
            endContent = {},
            onTap = {}
        )
    }
}