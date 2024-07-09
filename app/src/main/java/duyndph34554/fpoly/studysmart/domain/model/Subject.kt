package duyndph34554.fpoly.studysmart.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import duyndph34554.fpoly.studysmart.presentation.theme.gradient1
import duyndph34554.fpoly.studysmart.presentation.theme.gradient2
import duyndph34554.fpoly.studysmart.presentation.theme.gradient3
import duyndph34554.fpoly.studysmart.presentation.theme.gradient4
import duyndph34554.fpoly.studysmart.presentation.theme.gradient5

@Entity
data class Subject(
    val name: String,
    val goalHours: Float,
    val colors: List<Int>,
    @PrimaryKey(autoGenerate = true)
    val subjectId: Int? = null
) {
    companion object {
        val subjectCardColors = listOf(gradient1, gradient2, gradient3, gradient4, gradient5)
    }
}
