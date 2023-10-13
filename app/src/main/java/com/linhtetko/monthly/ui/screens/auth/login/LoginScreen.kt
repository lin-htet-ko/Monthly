package com.linhtetko.monthly.ui.screens.auth.login

import android.util.Log.d
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.linhtetko.monthly.R
import com.linhtetko.monthly.ui.components.HeaderText
import com.linhtetko.monthly.ui.components.LoadingDialog
import com.linhtetko.monthly.ui.components.PrimaryButton
import com.linhtetko.monthly.ui.components.Spacer1x
import com.linhtetko.monthly.ui.components.Spacer2x
import com.linhtetko.monthly.ui.components.TextFieldWithLabel
import com.linhtetko.monthly.ui.locale.language.Language
import com.linhtetko.monthly.ui.screens.Screen
import com.linhtetko.monthly.ui.theme.MonthlyTheme
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()
    val language by viewModel.languageBundle.collectAsState(null)

    if (viewModel.state.success == true) {
        navController.navigate(Screen.Main.route){
            popUpTo(navController.graph.id){
                saveState = false
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        if (viewModel.state.error != null)
            scaffoldState.snackbarHostState.showSnackbar(viewModel.state.error ?: "")
    }
    language?.apply {
        LoginScreenContent(
            modifier = modifier.fillMaxSize(),
            language = this.language,
            scaffoldState = scaffoldState,
            showSnackBar = viewModel.state.error,
            isLoading = viewModel.state.isLoading,
            email = viewModel.email,
            onEmailChange = {
                viewModel.email = it
            },
            password = viewModel.password,
            onPasswordChange = {
                viewModel.password = it
            },
            onLogin = {
                viewModel.login()
            },
            onDialogDismiss = {
                viewModel.state = viewModel.state.copy(isLoading = false)
            }
        )
    }
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState,
    language: Language,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    showSnackBar: String? = null,
    isLoading: Boolean,
    onDialogDismiss: () -> Unit
) {

    if (isLoading) {
        LoadingDialog(language = language, onTapDismiss = onDialogDismiss)
    }

    if (showSnackBar != null)
        LaunchedEffect(key1 = showSnackBar) {
            launch {
                scaffoldState.snackbarHostState.showSnackbar(showSnackBar)
            }
        }

    Scaffold(modifier = modifier, scaffoldState = scaffoldState) {
        Column(
            modifier = modifier
                .padding(it)
                .padding(dimensionResource(id = R.dimen.spacing_2x)),
            verticalArrangement = Arrangement.Center
        ) {
            HeaderText(text = language.lblLoginAcc)
            Spacer2x()
            TextFieldWithLabel(
                label = language.lblEmail,
                value = email,
                onValueChange = onEmailChange
            )
            Spacer2x()
            TextFieldWithLabel(
                label = language.lblPassword,
                value = password,
                onValueChange = onPasswordChange
            )
            Spacer1x()
            PrimaryButton(label = language.lblLogin, onTap = onLogin)
        }
    }
}