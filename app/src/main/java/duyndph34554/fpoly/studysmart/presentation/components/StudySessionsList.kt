package duyndph34554.fpoly.studysmart.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import duyndph34554.fpoly.studysmart.R
import duyndph34554.fpoly.studysmart.domain.model.Session
import duyndph34554.fpoly.studysmart.util.changeMillisToDateString
import duyndph34554.fpoly.studysmart.util.toHours

//LazyListScope tao ra danh sach linh hoat, quan ly cac phan tu cua ds 1 cach hieu qua
fun LazyListScope.studySessionsList(
    sectionTitle: String, //Tieu de danh sach
    emptyListText: String, //Van ban hien thi khi ds rong
    sessions: List<Session>, //Danh sach cac phien hoc
    onDeleteIconClick: (Session) -> Unit //Ham lambda xu ly su kien xoa
) {
//    item hien thi tieu de
    item {
        Text(
            text = sectionTitle,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(12.dp)
        )
    }

//    Kiem tra neu ds trong thi hien thị doan van ban
    if (sessions.isEmpty()) {
        item {
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(120.dp),
                    painter = painterResource(id = R.drawable.img_lamp),
                    contentDescription = emptyListText
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = emptyListText,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }

        }
    }
//    Neu ds khong rong thi hiển thi ds dung items de lap qua tung phan tu session
    items(sessions) {session ->
        StudySessionCard(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            session = session,
            onDeleteIconClick = {onDeleteIconClick(session)}
        )
    }
}

//item Session
@Composable
private fun StudySessionCard(
    modifier: Modifier = Modifier,
    session: Session, //khai bao doi tuong Session
    onDeleteIconClick: () -> Unit //ham lambda su ly su kien khi nhap vao Icon xoa
) {
    Card (
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (
                modifier = Modifier.padding(start = 12.dp)
            ) {
//                Name Session
                Text(
                    text = session.relatedToSubject,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(4.dp))
//                date study session
                Text(
                    text = session.date.changeMillisToDateString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.weight(1f))
//            Time study session
            Text(
                text = "${session.duration.toHours()} hr",
                style = MaterialTheme.typography.titleMedium
            )
//            Icon Delete
            IconButton(onClick = onDeleteIconClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Session"
                )
            }
        }
    }
}