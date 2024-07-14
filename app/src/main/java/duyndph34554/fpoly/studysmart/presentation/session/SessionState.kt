package duyndph34554.fpoly.studysmart.presentation.session

import duyndph34554.fpoly.studysmart.domain.model.Session
import duyndph34554.fpoly.studysmart.domain.model.Subject

data class SessionState(
    val subjects: List<Subject> = emptyList(),
    val sessions: List<Session> = emptyList(),
    val relatedSubject: String? = null,
    val subjectId: Int? = null,
    val session: Session? = null
)
