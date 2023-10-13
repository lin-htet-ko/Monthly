package com.linhtetko.monthly.ui.screens.auth.register

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.linhtetko.monthly.R
import com.linhtetko.monthly.ui.components.ComposableWithLabel
import com.linhtetko.monthly.ui.components.HeaderText
import com.linhtetko.monthly.ui.components.LoadingDialog
import com.linhtetko.monthly.ui.components.PrimaryButton
import com.linhtetko.monthly.ui.components.Spacer1x
import com.linhtetko.monthly.ui.components.Spacer2x
import com.linhtetko.monthly.ui.components.TextFieldWithLabel
import com.linhtetko.monthly.ui.locale.language.Language
import com.linhtetko.monthly.ui.screens.Screen
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel,
    navController: NavController
) {

    val languageBundle by viewModel.language.collectAsState(null)

    val context = LocalContext.current
    val imageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            if (it != null) {
                context.contentResolver.openInputStream(it)?.use { inputStream ->
                    val name = "${context.cacheDir}/${System.currentTimeMillis()}.png"
                    val file = File(name)
                    file.writeBytes(inputStream.readBytes())
                    viewModel.image = file
                }
            }
        }

    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()

    languageBundle?.language?.let { language ->
        if (viewModel.state.isLoading || viewModel.userState.isLoading) {
            LoadingDialog(language = language) {
                viewModel.state = viewModel.state.copy(isLoading = false)
                viewModel.userState = viewModel.userState.copy(isLoading = false)
            }
        }

        if (viewModel.userState.success != null) {
            navController.popBackStack()
            navController.navigate(Screen.Main.route)
        }

        LaunchedEffect(key1 = Unit) {
            var error: String? = null

            if (viewModel.state.error != null) error = viewModel.state.error
            if (viewModel.userState.error != null) error = viewModel.state.error

            if (error != null)
                scaffoldState.snackbarHostState.showSnackbar(error)

        }


        RegisterScreenContent(
            scaffoldState = scaffoldState,
            scrollState = scrollState,
            modifier = modifier
                .fillMaxSize(),
            name = viewModel.name,
            onNameChange = {
                viewModel.name = it
            },
            email = viewModel.email,
            onEmailChange = {
                viewModel.email = it
            },
            password = viewModel.password,
            onPasswordChange = {
                viewModel.password = it
            },
            phone = viewModel.phone,
            onPhoneChange = {
                viewModel.phone = it
            },
            image = viewModel.image,
            onAddImage = {
                imageLauncher.launch("image/*")
            },
            onRegister = {
                viewModel.register()
            },
            onTapLogin = {
                navController.navigate(Screen.Login.route)
            },
            isLoading = viewModel.state.isLoading,
            onTapLoadingDismiss = {
                viewModel.state = viewModel.state.copy(isLoading = true)
            },
            showSnackBar = viewModel.state.error,
            language = language
        )
    }
}

@Composable
fun RegisterScreenContent(
    modifier: Modifier = Modifier,
    language: Language,
    scrollState: ScrollState,
    scaffoldState: ScaffoldState,
    name: String,
    onNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    phone: String,
    onPhoneChange: (String) -> Unit,
    image: File?,
    isLoading: Boolean,
    onTapLoadingDismiss: () -> Unit,
    onAddImage: () -> Unit,
    onRegister: () -> Unit,
    onTapLogin: () -> Unit,
    showSnackBar: String? = null
) {
    if (isLoading) {
        LoadingDialog(language = language, onTapDismiss = onTapLoadingDismiss)
    }

    if (showSnackBar != null)
        LaunchedEffect(key1 = showSnackBar) {
            launch {
                scaffoldState.snackbarHostState.showSnackbar(showSnackBar)
            }
        }

    Scaffold(modifier = modifier.verticalScroll(scrollState)) {
        Column(
            modifier = modifier
                .padding(it)
                .padding(horizontal = dimensionResource(id = R.dimen.spacing_2x))
                .padding(top = dimensionResource(id = R.dimen.spacing_3x)),
            verticalArrangement = Arrangement.Center
        ) {
            HeaderText(text = language.lblRegisterAcc)
            Spacer2x()
            TextFieldWithLabel(
                label = language.appName,
                value = name,
                onValueChange = onNameChange
            )
            Spacer1x()
            TextFieldWithLabel(
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                label = language.lblEmail,
                value = email,
                onValueChange = onEmailChange
            )
            Spacer1x()
            TextFieldWithLabel(
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                label = language.lblPassword,
                value = password,
                onValueChange = onPasswordChange
            )
            Spacer1x()
            TextFieldWithLabel(
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                label = language.lblPhone,
                value = phone,
                onValueChange = onPhoneChange
            )
            Spacer1x()
            ComposableWithLabel(
                label = language.lblAddImage, modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.height_register_img))
            ) {
                if (image == null)
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = onAddImage),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_add_image),
                        contentDescription = language.lblAddImage,
                        contentScale = ContentScale.Crop
                    )
                else {
                    val bitmap = BitmapFactory.decodeFile(image.absolutePath)
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = onAddImage),
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = language.lblAddImage,
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Spacer1x()
            PrimaryButton(label = language.lblRegister, onTap = onRegister)
            OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = onTapLogin) {
                Text(text = language.lblLogin)
            }
        }
    }
}