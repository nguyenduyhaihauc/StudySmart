package duyndph34554.fpoly.studysmart.presentation.dashborad

import androidx.compose.ui.graphics.Color
import duyndph34554.fpoly.studysmart.domain.model.Session
import duyndph34554.fpoly.studysmart.domain.model.Task

//sealed class dinh nghia 1 lop niem phong
//viec su dung sealed class giup tang tinh an toan kieu type-safety va dam bao tat ca cac su kien
//duoc xu ly tai cung 1 cho khi su dung when expression
sealed class DashboardEvent {

//    data object cho phep tao ra 1 singleton object duoc su dung nhu 1 lop con cua DashboardEvent
    data object SaveSubject : DashboardEvent()

    data object DeleteSession : DashboardEvent()

//    data class tao lop vooi muc dich chinh laf de chua du lieu
    data class OnDeleteSessionButtonClick(val session: Session): DashboardEvent()

    data class OnTaskIsCompleteChange(val task: Task): DashboardEvent()

    data class OnSubjectCardColorChange(val colors: List<Color>): DashboardEvent()

    data class OnSubjectNameChange(val name: String): DashboardEvent()

    data class OnGoalStudyHoursChange(val hours: String): DashboardEvent()
}