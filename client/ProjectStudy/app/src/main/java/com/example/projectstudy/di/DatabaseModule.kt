package com.example.projectstudy.di

import android.content.Context
import androidx.room.Room
import com.example.projectstudy.data.local.ProjectStudyDatabase
import com.example.projectstudy.data.local.dao.GroupDao
import com.example.projectstudy.data.local.dao.RankingDao
import com.example.projectstudy.data.local.dao.StudyActivityDao
import com.example.projectstudy.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo responsável por fornecer as dependências relacionadas ao banco local.
 *
 * Este módulo configura a instância principal do Room Database e disponibiliza
 * os DAOs usados pelos repositórios da aplicação.
 *
 * Como o app segue uma abordagem offline-first, o banco local é a fonte principal
 * de leitura para telas como feed, ranking, perfil e registro de sessões.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Fornece a instância principal do banco local da aplicação.
     *
     * A anotação [Singleton] garante que apenas uma instância de
     * [ProjectStudyDatabase] seja criada e reutilizada durante todo o ciclo de
     * vida do app.
     *
     * O [ApplicationContext] é usado porque o banco deve estar associado ao
     * contexto da aplicação, e não a uma Activity específica.
     *
     * @param context Contexto global da aplicação.
     * @return Instância configurada do banco local Room.
     */
    @Provides
    @Singleton
    fun provideProjectStudyDatabase(
        @ApplicationContext context: Context
    ): ProjectStudyDatabase {
        return Room.databaseBuilder(
            context,
            ProjectStudyDatabase::class.java,
            "project_study.db"
        ).build()
    }

    /**
     * Fornece o DAO de usuários.
     *
     * Esse DAO permite consultar e salvar dados da tabela `users`.
     *
     * @param database Instância principal do banco local.
     * @return DAO responsável pelas operações de usuários.
     */
    @Provides
    fun provideUserDao(
        database: ProjectStudyDatabase
    ): UserDao {
        return database.userDao()
    }

    /**
     * Fornece o DAO de grupos.
     *
     * Esse DAO permite consultar e salvar dados da tabela `groups`.
     *
     * @param database Instância principal do banco local.
     * @return DAO responsável pelas operações de grupos.
     */
    @Provides
    fun provideGroupDao(
        database: ProjectStudyDatabase
    ): GroupDao {
        return database.groupDao()
    }

    /**
     * Fornece o DAO de atividades de estudo.
     *
     * Esse DAO permite manipular atividades, vínculos entre atividades e grupos,
     * além das mídias anexadas às sessões.
     *
     * @param database Instância principal do banco local.
     * @return DAO responsável pelas operações de atividades de estudo.
     */
    @Provides
    fun provideStudyActivityDao(
        database: ProjectStudyDatabase
    ): StudyActivityDao {
        return database.studyActivityDao()
    }

    /**
     * Fornece o DAO de ranking.
     *
     * Esse DAO permite consultar e atualizar as classificações dos usuários
     * dentro dos grupos de estudo.
     *
     * @param database Instância principal do banco local.
     * @return DAO responsável pelas operações de ranking.
     */
    @Provides
    fun provideRankingDao(
        database: ProjectStudyDatabase
    ): RankingDao {
        return database.rankingDao()
    }
}