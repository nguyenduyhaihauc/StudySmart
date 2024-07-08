package duyndph34554.fpoly.studysmart.data.local

import androidx.room.Dao
import androidx.room.Upsert
import duyndph34554.fpoly.studysmart.domain.model.Subject

@Dao
interface SubjectDao {

    @Upsert
    fun upsertSubject(subject: Subject)
    
    fun getTotalSubjectCount(): Int

    fun getTotalGoalHours(): Float

    fun getSubjectById(subjectId: Int): Subject?

    fun deleteSubject(subjectId: Int)

    fun getAllSubject(): List<Subject>

}