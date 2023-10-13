package com.linhtetko.monthly.ui.screens.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.linhtetko.monthly.R
import com.linhtetko.monthly.data.model.DashboardModel
import com.linhtetko.monthly.data.model.ItemModel
import com.linhtetko.monthly.data.model.UserModel
import com.linhtetko.monthly.ui.components.BoughtItem
import com.linhtetko.monthly.ui.components.ComposableWithLabel
import com.linhtetko.monthly.ui.components.ComposableWithLabelAndAction
import com.linhtetko.monthly.ui.components.ContentLoading
import com.linhtetko.monthly.ui.components.FlatmateItem
import com.linhtetko.monthly.ui.components.HeaderText
import com.linhtetko.monthly.ui.components.HorizontalSpacer2x
import com.linhtetko.monthly.ui.components.Spacer1x
import com.linhtetko.monthly.ui.components.Spacer2x
import com.linhtetko.monthly.ui.locale.language.Language
import com.linhtetko.monthly.ui.screens.Screen
import com.linhtetko.monthly.utils.ScreenState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = viewModel) {
        var error: String? = null
        if (viewModel.dashboardState.error != null) error = viewModel.dashboardState.error
        if (viewModel.flatMatesState.error != null) error = viewModel.flatMatesState.error
        if (viewModel.generalItemsState.error != null) error = viewModel.generalItemsState.error
        if (error != null)
            scaffoldState.snackbarHostState.showSnackbar(error)
    }

    if (viewModel.isLogout) {
        navController.navigate(Screen.Register.route)
    }

    val languageBundle by viewModel.languageBundle.collectAsState(null)


    languageBundle?.apply {
        HomeScreenContent(
            modifier = modifier,
            scaffoldState = scaffoldState,
            language = language,
            dashboardState = viewModel.dashboardState,
            flatState = viewModel.flatMatesState,
            generalItemState = viewModel.generalItemsState,
            boughtItemState = viewModel.itemsState,
            isExpand = viewModel.isExpand,
            onTapExpandable = viewModel::onTapExpandable,
            isBoughtItemExpand = viewModel.isBoughtItemExpand,
            onTapBoughtItemExpandable = viewModel::onTapBoughtItemExpand,
            onTapUser = {
                if (it.id != null)
                    navController.navigate(Screen.Profile.route(it.id!!))
            },
            onTapNewItemModel = {
                navController.navigate(Screen.CreateItem.route(Screen.CreateItem.PATH_ITEM))
            },
            onTapNewGeneralItem = {
                navController.navigate(Screen.CreateItem.route(Screen.CreateItem.PATH_GENERAL))
            },
            onTapLogout = viewModel::logout,
            onTapSetting = {
                navController.navigate(Screen.Setting.route)
            }
        )
    }
}


@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState,
    language: Language,
    dashboardState: ScreenState<DashboardModel>,
    flatState: ScreenState<List<UserModel>>,
    generalItemState: ScreenState<List<ItemModel>>,
    boughtItemState: ScreenState<List<ItemModel>>,
    isExpand: Boolean,
    onTapExpandable: () -> Unit,
    isBoughtItemExpand: Boolean,
    onTapBoughtItemExpandable: () -> Unit,
    onTapUser: (UserModel) -> Unit,
    onTapNewItemModel: () -> Unit,
    onTapNewGeneralItem: () -> Unit,
    onTapSetting: () -> Unit,
    onTapLogout: () -> Unit,
) {
    Scaffold(
        modifier = modifier.background(MaterialTheme.colors.background),
        scaffoldState = scaffoldState
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.spacing_2x))
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    HeaderText(
                        text = language.appName,
                        color = MaterialTheme.colors.primary
                    )
                    Row {
                        IconButton(onClick = onTapSetting) {
                            Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                        }
                        IconButton(onClick = onTapLogout) {
                            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = null)
                        }
                    }
                }
            }
            item {
                if (dashboardState.isLoading) {
                    ContentLoading()
                } else if (dashboardState.success != null) {
                    HomeDashboard(
                        language = language,
                        total = dashboardState.success.total.toDouble(),
                        each = dashboardState.success.each
                    )
                }
            }
            item {
                if (flatState.isLoading) {
                    ContentLoading()
                } else if (flatState.success != null)
                    Flatmates(
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.spacing_2x)),
                        language = language,
                        items = flatState.success,
                        onTap = onTapUser
                    )
            }
            item {
                Spacer2x()
            }

            item {
                if (generalItemState.isLoading) {
                    ContentLoading()
                } else if (generalItemState.success != null)
                    GeneralCost(
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.spacing_2x)),
                        language = language,
                        items = generalItemState.success.reversed(),
                        onTapAddNew = onTapNewGeneralItem,
                        onTapExpandable = onTapExpandable,
                        isExpand = isExpand
                    )

            }
            item {
                Spacer2x()
            }

            item {
                if (boughtItemState.isLoading) {
                    ContentLoading()
                } else if (boughtItemState.success != null)
                    BoughtItems(
                        language = language,
                        items = boughtItemState.success.reversed(),
                        onTapNew = onTapNewItemModel,
                        isExpand = isBoughtItemExpand,
                        onTapExpandable = onTapBoughtItemExpandable
                    )
            }
        }
    }
}

@Composable
fun BoughtItems(
    language: Language,
    isExpand: Boolean,
    onTapExpandable: () -> Unit,
    items: List<ItemModel>,
    onTapNew: () -> Unit
) {
    ComposableWithLabelAndAction(
        labelContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = language.lblBoughtItems)
                IconButton(onClick = onTapExpandable) {
                    Icon(
                        imageVector = if (isExpand) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            }
        },
        actionLabel = language.lblAddNew,
        onTapAction = onTapNew
    ) {
        val tempItems = if (isExpand) items else items.take(4)
        if (tempItems.isNotEmpty()) {
            val size = if (tempItems.size % 2 != 0) tempItems.size + 1 else tempItems.size
            LazyVerticalGrid(
                modifier = Modifier
                    .requiredHeight((size * 100).dp)
                    .animateContentSize(
                        animationSpec = tween(
                            easing = LinearOutSlowInEasing
                        )
                    ),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_general)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_general))
            ) {
                items(tempItems) { item ->
                    BoughtItem(item = item)
                }
            }
        } else {
            EmptyData(label = language.lblEmptyBoughtItems)
        }
    }
}

@Composable
private fun GeneralCostTableHeader(modifier: Modifier = Modifier, language: Language) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colors.primary.copy(alpha = 0.1f))
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.spacing_2x))
    ) {
        Text(modifier = Modifier.weight(1f), text = language.lblCategory)
        Text(text = language.lblCost)
    }
}

@Composable
private fun GeneralCostTableRow(modifier: Modifier = Modifier, category: String, cost: Double) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.spacing_2x),
                vertical = dimensionResource(id = R.dimen.spacing_general)
            )
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = category,
            fontWeight = FontWeight.Light
        )
        Text(text = cost.toString())
    }
}

@Composable
private fun GeneralCostFooter(modifier: Modifier = Modifier, language: Language, total: Double) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = language.lblTotal,
            textAlign = TextAlign.End
        )
        HorizontalSpacer2x()
        Text(text = total.toString())
    }
}

@Composable
fun GeneralCost(
    modifier: Modifier = Modifier,
    language: Language,
    items: List<ItemModel> = emptyList(),
    isExpand: Boolean,
    onTapExpandable: () -> Unit,
    onTapAddNew: () -> Unit
) {
    ComposableWithLabelAndAction(
        modifier = modifier,
        labelContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = language.lblGeneralCost)
                IconButton(onClick = onTapExpandable) {
                    Icon(
                        imageVector = if (isExpand) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            }
        },
        actionLabel = language.lblAddNew,
        onTapAction = onTapAddNew
    ) {
        if (items.isNotEmpty()) {
            GeneralCostTableHeader(language = language)
            var temp = items
            if (!isExpand)
                temp = items.take(3)

            temp.forEach {
                GeneralCostTableRow(
                    category = it.label ?: "",
                    cost = (it.price ?: 0.0).toDouble()
                )
            }
            Spacer1x()
            GeneralCostFooter(
                language = language,
                total = items.sumOf { (it.price ?: 0.0).toDouble() })
        } else {
            EmptyData(label = language.lblEmptyCategory)
        }
    }
}

@Composable
fun Flatmates(
    modifier: Modifier = Modifier,
    language: Language,
    items: List<UserModel>,
    onTap: (UserModel) -> Unit
) {
    ComposableWithLabel(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp),
        label = language.lblFlatmates
    ) {
        LazyHorizontalGrid(
            modifier = Modifier.fillMaxWidth(),
            rows = GridCells.Fixed(if (items.size > 1) 2 else 1),
            verticalArrangement = Arrangement.SpaceBetween,
            contentPadding = PaddingValues(0.dp)
        ) {
            items(items) {
                FlatmateItem(
                    image = it.imgPath ?: "",
                    name = it.name ?: "",
                    onTap = { onTap.invoke(it) })
            }
        }
    }
}

@Composable
fun HomeDashboard(modifier: Modifier = Modifier, language: Language, total: Double, each: Double) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        border = BorderStroke(
            width = dimensionResource(id = R.dimen.size_general_stroke),
            color = MaterialTheme.colors.primary.copy(alpha = 0.5f)
        ),
        backgroundColor = Color.Gray.copy(alpha = 0.04f)
    ) {
        Row(
            modifier = modifier.padding(
                horizontal = dimensionResource(id = R.dimen.spacing_2x),
                vertical = dimensionResource(id = R.dimen.spacing_3x)
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.undraw_team_work_k_80_m_1),
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(id = R.dimen.size_dashboard_img))
            )
            HorizontalSpacer2x()
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = language.lblAmountToPay,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    textAlign = TextAlign.Center
                )
                Spacer2x()
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    DashboardItem(
                        modifier = Modifier.weight(1f),
                        label = language.lblTotal,
                        value = total.toString() + language.lblKs
                    )
                    DashboardItem(
                        modifier = Modifier.weight(1f),
                        label = language.lblEach,
                        value = each.toString() + language.lblKs
                    )
                }
            }
        }
    }
}

@Composable
fun DashboardItem(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label)
        Text(text = value)
    }
}

@Composable
fun EmptyData(modifier: Modifier = Modifier, label: String) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(MaterialTheme.colors.primary.copy(alpha = 0.1f))
            .padding(
                dimensionResource(id = R.dimen.spacing_2x)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
        )
    }
}