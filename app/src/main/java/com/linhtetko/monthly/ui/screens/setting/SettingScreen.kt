package com.linhtetko.monthly.ui.screens.setting

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.linhtetko.monthly.R
import com.linhtetko.monthly.MainViewModel
import com.linhtetko.monthly.ui.components.ColorListItem
import com.linhtetko.monthly.ui.components.HeaderText
import com.linhtetko.monthly.ui.components.SettingItem
import com.linhtetko.monthly.ui.components.Spacer2x
import com.linhtetko.monthly.ui.locale.language.Language
import com.linhtetko.monthly.ui.locale.language.LanguageBundle
import com.linhtetko.monthly.ui.locale.language.LanguageType
import com.linhtetko.monthly.ui.screens.Screen
import com.linhtetko.monthly.ui.theme.ColorItem
import com.linhtetko.monthly.ui.theme.MonthlyTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navController: NavController
) {

    val languages by viewModel.languages.collectAsState(listOf())
    val selectedLanguage by viewModel.selectedLanguage.collectAsState(null)

    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    AnimatedVisibility(visible = viewModel.showLanguageDialog) {
        LanguageDialog(
            bundles = languages,
            selectedItem = selectedLanguage,
            onDismissRequest = { viewModel.showLanguageDialog = false },
            onTapItem = {
                viewModel.saveLanguage(it)
                viewModel.showLanguageDialog = false
            }
        )
    }

    if (viewModel.isLogout) {
        navController.navigate(Screen.Register.route) {
            popUpTo(navController.graph.id) {
                saveState = true
            }
        }
    }
    selectedLanguage?.language?.apply {
        BottomSheetScaffold(
            modifier = modifier.fillMaxSize(),
            sheetPeekHeight = dimensionResource(id = R.dimen.size_empty),
            scaffoldState = scaffoldState,
            sheetContent = {
                ColorsBottomSheet(
                    colors = viewModel.colors,
                    language = this@apply,
                    selectedItem = ColorItem.map(viewModel.currentSelectedColor),
                    onItemSelected = viewModel::onSelectColor
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
            ) {
                HeaderText(
                    text = lblSetting,
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.spacing_3x))
                        .padding(top = dimensionResource(id = R.dimen.spacing_3x))
                )
                Spacer2x()
                SettingItem(
                    text = lblChangeLang,
                    onTap = { viewModel.showLanguageDialog = true }
                ) {
                    Text(text = selectedLanguage?.type?.name ?: "--")
                }

                SettingItem(
                    text = lblChangeColor,
                    onTap = {
                        coroutineScope.launch {
                            val state = scaffoldState.bottomSheetState
                            if (state.isExpanded)
                                state.collapse()
                            else
                                state.expand()
                        }
                    }
                )

                SettingItem(
                    text = lblLogout,
                    onTap = viewModel::logout
                )
            }
        }
    }
}

@Composable
fun ColorsBottomSheet(
    language: Language,
    colors: List<ColorItem>,
    selectedItem: ColorItem,
    onItemSelected: (ColorItem) -> Unit
) {
    Column(modifier = Modifier.background(MaterialTheme.colors.onBackground.copy(alpha = 0.05f))) {
        Text(
            text = language.lblChooseColor,
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.spacing_2x))
                .padding(top = dimensionResource(id = R.dimen.spacing_3x)),
            fontSize = MaterialTheme.typography.h5.fontSize
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.spacing_3x)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_2x)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_2x))
        ) {
            items(colors) {
                ColorListItem(item = it, isSelected = it == selectedItem, onSelect = onItemSelected)
            }
        }
    }
}

@Composable
private fun LanguageDialog(
    modifier: Modifier = Modifier,
    bundles: List<LanguageBundle>,
    selectedItem: LanguageBundle?,
    onDismissRequest: () -> Unit,
    onTapItem: (item: LanguageType) -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(dimensionResource(id = R.dimen.spacing_2x))
        ) {
            Text(
                text = selectedItem?.language?.lblChooseLang
                    ?: stringResource(id = R.string.lbl_choose_language),
                color = MaterialTheme.colors.onBackground,
                fontSize = MaterialTheme.typography.h6.fontSize
            )
            bundles.forEachIndexed { _, item ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = item.type == selectedItem?.type,
                        onClick = { onTapItem(item.type) })
                    Text(text = item.type.name)
                }
            }
        }
    }
}

@Preview
@Composable
private fun SettingScreenPreview() {
    MonthlyTheme {
        SettingScreen(viewModel = hiltViewModel(), navController = rememberNavController())
    }
}