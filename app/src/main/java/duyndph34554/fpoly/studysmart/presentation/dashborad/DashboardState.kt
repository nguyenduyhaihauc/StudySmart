package duyndph34554.fpoly.studysmart.presentation.dashborad

import androidx.compose.ui.graphics.Color
import duyndph34554.fpoly.studysmart.domain.model.Session
import duyndph34554.fpoly.studysmart.domain.model.Subject

//data class duoc tao ra voi muc dich chinh la chua du lieu
data class DashboardState(
    val totalSubjectCount: Int = 0, //tong so mon hoc
    val totalStudiedHours: Float = 0f, //tong so gio da hoc
    val totalGoalStudyHours: Float = 0f, // tong so gio dat ra muc tieu hoc
    val subjects: List<Subject> = emptyList(), //Danh sach Session voi khoi tao la 1 ds rong
    val subjectName: String = "",
    val goalStudyHours: String = "",
    val subjectCardColors: List<Color> = Subject.subjectCardColors.random(),
    val session: Session? = null
)