package duyndph34554.fpoly.studysmart.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteDialog(
    isOpen: Boolean, //Kiem tra de mo hop thoai
    title: String, //Tieu de dialog
    bodyText: String, //Text trong body Dialog
    onDismissRequest: () -> Unit, //Ham callback xu ly dong hop thoai
    onConfirmButtonClick: () -> Unit // Ham callback xu ly nut xac nhan xoa
) {
    if (isOpen) {
        AlertDialog(
            title = { Text(text = title) },
            text = {
                Text(text = bodyText)
            },
            onDismissRequest = onDismissRequest,
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Cancel")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = onConfirmButtonClick
                ) {
                    Text(text = "Delete")
                }
            }
        )
    }
}