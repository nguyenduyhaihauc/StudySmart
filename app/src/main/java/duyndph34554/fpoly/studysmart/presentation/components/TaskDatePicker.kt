package duyndph34554.fpoly.studysmart.presentation.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDatePicker(
    state: DatePickerState,
    isOpen: Boolean,
    confirmButtonText: String = "Ok",
    dismissButtonText: String = "Cancel",
    onDismissRequest: () -> Unit,
    onConfirmButtonClicked: (selectedDate: LocalDate) -> Unit
) {
    val selectedDate = remember { mutableStateOf<LocalDate?>(null) }

    if (isOpen) {
        DatePickerDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = {
                    val dateInMillis = state.selectedDateMillis
                    if (dateInMillis != null) {
                        val date = Instant.ofEpochMilli(dateInMillis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        if (date >= LocalDate.now(ZoneId.systemDefault())) {
                            selectedDate.value = date
                            onConfirmButtonClicked(date)
                        } else {
                            // Handle invalid date selection here
                        }
                    }
                }) {
                    Text(text = confirmButtonText)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = dismissButtonText)
                }
            },
            content = {
                DatePicker(
                    state = state
                )
            }
        )
    }
}