package com.linhtetko.monthly.ui.screens.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.linhtetko.monthly.R
import com.linhtetko.monthly.ui.components.HeaderText
import com.linhtetko.monthly.ui.components.LoadingDialog
import com.linhtetko.monthly.ui.components.PrimaryButton
import com.linhtetko.monthly.ui.components.Spacer2x
import com.linhtetko.monthly.ui.components.TextFieldWithLabel
import com.linhtetko.monthly.ui.locale.language.Language
import com.linhtetko.monthly.ui.screens.Screen
import com.linhtetko.monthly.ui.theme.MonthlyTheme

@Composable
fun CreateGeneralItemScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateItemViewModel,
    navController: NavController,
    type: String? = null
) {

    val languageBundle by viewModel.language.collectAsState(null)

    if (viewModel.state.success == true) {
        navController.navigate(Screen.Main.route){
            popUpTo(navController.graph.id){
                saveState = false
            }
        }
    }


    languageBundle?.apply {
        if (viewModel.state.isLoading) {
            LoadingDialog(language = language) {
                viewModel.state = viewModel.state.copy(isLoading = false)
            }
        }

        val scaffoldState = rememberScaffoldState()

        LaunchedEffect(key1 = Unit) {
            if (viewModel.state.error != null) {
                scaffoldState.snackbarHostState.showSnackbar(viewModel.state.error ?: "")
            }
        }

        val typeLabel =
            if (type == Screen.CreateItem.PATH_GENERAL) language.lblGeneralCost else language.lblBoughtItems

        Scaffold(modifier = modifier) {
            CreateGeneralItemContent(
                language = language,
                typeLabel = typeLabel,
                modifier = Modifier.padding(it),
                category = viewModel.label,
                onChangeCategory = { category -> viewModel.label = category },
                cost = viewModel.price,
                onChangeCost = { cost -> viewModel.price = cost },
                onTapAdd = {
                    if (type != null) {
                        viewModel.createItem(type)
                    }
                }
            )
        }
    }
}

@Composable
fun CreateGeneralItemContent(
    typeLabel: String,
    modifier: Modifier = Modifier,
    language: Language,
    category: String,
    onChangeCategory: (String) -> Unit,
    cost: String,
    onChangeCost: (String) -> Unit,
    onTapAdd: () -> Unit
) {
    Scaffold(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(
                    vertical = dimensionResource(id = R.dimen.spacing_3x),
                    horizontal = dimensionResource(id = R.dimen.spacing_2x)
                )
        ) {
            HeaderText(text = typeLabel)
            Spacer2x()
            Spacer2x()
            TextFieldWithLabel(
                label = language.lblAddCategoryName,
                value = category,
                onValueChange = onChangeCategory
            )
            Spacer2x()
            TextFieldWithLabel(
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                label = language.lblAddCost,
                value = cost,
                onValueChange = onChangeCost
            )
            Spacer2x()
            PrimaryButton(label = language.lblAdd, onTap = onTapAdd)
        }
    }
}

@Preview
@Composable
fun CreateGeneralItemScreenPreview() {
    MonthlyTheme {
        CreateGeneralItemScreen(
            viewModel = hiltViewModel(),
            navController = rememberNavController()
        )
    }
}