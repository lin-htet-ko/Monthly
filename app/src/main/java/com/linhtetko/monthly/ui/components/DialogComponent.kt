package com.linhtetko.monthly.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.linhtetko.monthly.R
import com.linhtetko.monthly.ui.locale.language.Language
import com.linhtetko.monthly.ui.theme.MonthlyTheme

@Composable
fun LoadingDialog(
    modifier: Modifier = Modifier,
    language: Language,
    onTapDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onTapDismiss) {
        Surface {
            Column(
                modifier = modifier.size(dimensionResource(id = R.dimen.size_progress_dialog)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = language.lblPlsWait)
                Spacer2x()
                CircularProgressIndicator()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingDialogPreview() {
//    MonthlyTheme {
//        LoadingDialog(onTapDismiss = {})
//    }
}