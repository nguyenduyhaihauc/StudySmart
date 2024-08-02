package duyndph34554.fpoly.studysmart.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import duyndph34554.fpoly.studysmart.domain.model.Subject

@Composable
fun AddSubjectDialog(
    isOpen: Boolean, //Xác dinh hop thoai Dialog co duoc mo hay khong
    title: String = "Add/Update Subject",
    selectedColors: List<Color>, //Lua chon mau sac dai dien cho mon hoc
    subjectName: String, //Ten mon hoc
    goalHours: String, //Dat muc tieu thoi gian de hoc
    onColorChange: (List<Color>) -> Unit, //Ham callback xu ly chon mau sac
    onSubjectNameChange: (String) -> Unit, //ham callback xu ly update ten mon hoc
    onGoalHoursChange: (String) -> Unit, // ham callback update thoi gian hoc
    onDismissRequest: () -> Unit, //Ham callback xu ly huy hop thoai
    onConfirmButtonClick: () -> Unit //ham callback xu ly xac nhan
) {
    var subjectNameError by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    var goalHoursError by rememberSaveable {
        mutableStateOf<String?>(null)
    }

//    Kiem tra do hop le khi nhap ten mon hoc
    subjectNameError = when {
        subjectName.isBlank() -> "Please enter subject name."
        subjectName.length < 2 -> "Subject name is too short."
        subjectName.length > 20 -> "Subject name is too long."
        else -> null
    }

//    Kiem tra hop le khi nhap gio hoc
    goalHoursError = when {
        goalHours.isBlank() -> "Please enter goal study hours."
        goalHours.toFloatOrNull() == null -> "Invalid number."
        goalHours.toFloat() < 1f -> "Please set at least 1 hour."
        goalHours.toFloat() > 1000f -> "Please set a maximum of 1000 hours."
        else -> null
    }

    if (isOpen) {
        AlertDialog(
            title = { Text(text = title)},
            text = {
                Column {
//                    Hien thi danh sach mau sac de lua chon
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
//                        Danh sach mau duoc thiet lap trong subject su dung forEach de lap
//                        qua tung phan tu, colors dai dien cho moi phan tu trong danh sach
                        Subject.subjectCardColors.forEach { colors ->
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape) //cat Box thanh hinh tron
                                    .border(
                                        width = 1.dp,
//                                        Neu mau duoc lua chon thi vien se la mau den
//                                        nguoc lai se de mau trong suot
                                        color = if (colors == selectedColors) Color.Black
                                        else Color.Transparent,
                                        shape = CircleShape
                                    )
//                             brush duoc su dung de to mau nen cua thanh phan giao dien nguoi dung
//                                gradient mau chuyen tiep
//                           Brush.verticalGradient chuyen doi mau này sang mau khac theo chieu doc
                                    .background(brush = Brush.verticalGradient(colors))
                                    .clickable { onColorChange(colors) }
                            )
                        }
                    }
                    OutlinedTextField(
                        value = subjectName, // noi dung van ban truong nhap hien tai
                        onValueChange = onSubjectNameChange, //Ham callback duoc goi moi khi truong nhap lieu thay doi
                        label = { Text(text = "Subject Name")},
                        singleLine = true, // chi duoc nhap tren 1 dong
//                      isErrpr la 1 truong thuoc tinh boolearn kiem tra truong nhap lieu
//                        co loi hay khong
                        isError = subjectNameError != null && subjectName.isNotBlank(),
//                      supportingText la van ban ho tro o duoi o nhap lieu
//                        orEmpty() tranh loi null
                        supportingText = { Text(text = subjectNameError.orEmpty())}
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = goalHours,
                        onValueChange = onGoalHoursChange,
                        label = { Text(text = "Goal Study Hour")},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = goalHoursError != null && goalHours.isNotBlank(),
                        supportingText = { Text(text = goalHoursError.orEmpty())}
                    )
                }
            },
            onDismissRequest = onDismissRequest,
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Cancel")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = onConfirmButtonClick,
//                 enabled de xac dinh nut Save co duoc kich hoat hay khong
//                    Neu ca hai truong nhap lieu khong loi thi nut Save duoc hien thi
                    enabled = subjectNameError == null && goalHoursError == null
                ) {
                    Text(text = "Save")
                }
            }
        )
    }
}