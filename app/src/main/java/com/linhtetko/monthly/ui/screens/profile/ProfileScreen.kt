package com.linhtetko.monthly.ui.screens.profile

import android.content.Intent
import android.net.Uri
import android.util.Log.d
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.linhtetko.monthly.R
import com.linhtetko.monthly.data.model.ItemModel
import com.linhtetko.monthly.data.model.UserModel
import com.linhtetko.monthly.ui.components.BoughtItem
import com.linhtetko.monthly.ui.components.ComposableWithLabel
import com.linhtetko.monthly.ui.components.ComposableWithLabelAndAction
import com.linhtetko.monthly.ui.components.ContentLoading
import com.linhtetko.monthly.ui.components.HeaderText
import com.linhtetko.monthly.ui.components.HorizontalSpacer2x
import com.linhtetko.monthly.ui.components.NetworkImage
import com.linhtetko.monthly.ui.locale.language.Language
import com.linhtetko.monthly.ui.screens.home.EmptyData
import com.linhtetko.monthly.ui.theme.MonthlyTheme
import com.linhtetko.monthly.utils.ScreenState
import java.text.DecimalFormat

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel,
    navController: NavController,
    uid: String
) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val launcherPhonePermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted){
                if (viewModel.phoneNumber != null) {
                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse("tel:${viewModel.phoneNumber}")
                    context.startActivity(intent)
                }
            }
        }
    )

    val languageBundle by viewModel.language.collectAsState(null)

    LaunchedEffect(key1 = Unit) {
        var error: String? = null

        if (viewModel.userState.error != null) error = viewModel.userItemsState.error
        if (viewModel.userItemsState.error != null) error = viewModel.userItemsState.error

        if (error != null) {
            scaffoldState.snackbarHostState.showSnackbar(error)
        }
    }

    languageBundle?.apply {
        ProfileScreenContent(
            modifier,
            language = language,
            scaffoldState = scaffoldState,
            userState = viewModel.userState,
            itemsState = viewModel.userItemsState,
            onTapBack = {
                navController.navigateUp()
            },
            onTapPhone = {
                viewModel.phoneNumber = it
                launcherPhonePermission.launch(android.Manifest.permission.CALL_PHONE)
            }
        )
    }
}

@Composable
private fun ProfileScreenContent(
    modifier: Modifier,
    scaffoldState: ScaffoldState,
    language: Language,
    userState: ScreenState<UserModel>,
    itemsState: ScreenState<List<ItemModel>>,
    onTapBack: () -> Unit,
    onTapPhone: (String) -> Unit
) {
    Scaffold(modifier = modifier, scaffoldState = scaffoldState, topBar = {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.1f),
            elevation = dimensionResource(id = R.dimen.size_empty),
            title = { Text(text = language.lblProfileDetail) },
            navigationIcon = {
                IconButton(onClick = onTapBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )
    }) {
        Column(modifier = Modifier.padding(it)) {
            if (userState.isLoading) {
                ContentLoading()
            } else if (userState.success != null)
                ProfileDashboard(user = userState.success, onTapPhone = onTapPhone)

            if (itemsState.isLoading) {
                ContentLoading()
            } else if (itemsState.success != null) {
                val totalPrice = itemsState.success.sumOf { item -> item.price ?: 0 }
                val formattedPrice = DecimalFormat("###.##").format(totalPrice)
                val textPadding = Modifier
                    .padding(
                        end = dimensionResource(id = R.dimen.spacing_2x)
                    )
                    .align(Alignment.End)

                Text(
                    text = language.lblUsedMoney,
                    modifier = textPadding.padding(top = dimensionResource(id = R.dimen.spacing_3x)),
                    textAlign = TextAlign.End
                )
                HeaderText(text = formattedPrice, modifier = textPadding)
                BoughtItems(language = language, items = itemsState.success)
            }
        }
    }
}

@Composable
private fun BoughtItems(modifier: Modifier = Modifier, language: Language, items: List<ItemModel>) {
    ComposableWithLabel(
        modifier = modifier
            .padding(horizontal = dimensionResource(id = R.dimen.spacing_general))
            .padding(
                bottom = dimensionResource(id = R.dimen.spacing_general)
            ),
        label = language.lblBoughtItems
    ) {
        if (items.isEmpty())
            EmptyData(label = language.lblEmptyBoughtItems)
        else
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_general)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_general))
            ) {
                if (items.isNotEmpty()) {
                    items(items) {
                        BoughtItem(item = it)
                    }
                }
            }
    }
}

@Composable
private fun ProfileDashboard(
    modifier: Modifier = Modifier,
    user: UserModel,
    onTapPhone: (String) -> Unit
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colors.primary.copy(alpha = 0.1f))
            .fillMaxWidth()
            .requiredHeight(dimensionResource(id = R.dimen.size_profile_img))
            .padding(horizontal = dimensionResource(id = R.dimen.spacing_2x)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NetworkImage(
            modifier = Modifier
                .size(100.dp)
                .border(
                    width = dimensionResource(id = R.dimen.size_general_stroke),
                    color = MaterialTheme.colors.primary
                ),
            data = user.imgPath ?: ""
        )
        HorizontalSpacer2x()
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = user.name ?: "")
            Text(text = user.email ?: "")
            Text(
                modifier = Modifier.clickable { if (user.phone != null) onTapPhone(user.phone) },
                text = user.phone ?: "",
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    MonthlyTheme {
        ProfileScreen(
            viewModel = hiltViewModel(),
            navController = rememberNavController(),
            uid = ""
        )
    }
}