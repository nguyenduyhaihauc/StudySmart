package duyndph34554.fpoly.studysmart.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import duyndph34554.fpoly.studysmart.data.repository.SessionRepositoryImpl
import duyndph34554.fpoly.studysmart.data.repository.SubjectRepositoryImpl
import duyndph34554.fpoly.studysmart.data.repository.TaskRepositoryImpl
import duyndph34554.fpoly.studysmart.domain.repository.SessionRepository
import duyndph34554.fpoly.studysmart.domain.repository.SubjectRepository
import duyndph34554.fpoly.studysmart.domain.repository.TaskRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindSubjectRepository(
        impl: SubjectRepositoryImpl
    ): SubjectRepository

    @Singleton
    @Binds
    abstract fun bindTaskRepository(
        impl: TaskRepositoryImpl
    ): TaskRepository

    @Singleton
    @Binds
    abstract fun bindSessionRepository(
        impl: SessionRepositoryImpl
    ): SessionRepository
}