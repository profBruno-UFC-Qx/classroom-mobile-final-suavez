package com.example.projectstudy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.projectstudy.data.local.dao.GroupDao
import com.example.projectstudy.data.local.dao.RankingDao
import com.example.projectstudy.data.local.dao.StudyActivityDao
import com.example.projectstudy.data.local.dao.UserDao
import com.example.projectstudy.data.local.entity.ActivityGroupCrossRef
import com.example.projectstudy.data.local.entity.ActivityMediaEntity
import com.example.projectstudy.data.local.entity.GroupEntity
import com.example.projectstudy.data.local.entity.RankingEntryEntity
import com.example.projectstudy.data.local.entity.StudyActivityEntity
import com.example.projectstudy.data.local.entity.UserEntity

/**
 * Banco de dados local principal do app ProjectStudy.
 *
 * Essa classe configura o Room Database usado pela camada offline-first do app.
 * Ela concentra as entidades locais e fornece acesso aos DAOs responsáveis por
 * usuários, grupos, atividades de estudo e ranking.
 *
 * O banco local é a fonte primária de dados para a interface. Assim, as telas
 * observam os dados salvos no Room e são atualizadas automaticamente quando
 * novas informações são inseridas ou alteradas.
 *
 * Entidades principais:
 * - [UserEntity]: usuário local.
 * - [GroupEntity]: grupos de estudo.
 * - [StudyActivityEntity]: sessões e atividades de estudo.
 * - [ActivityGroupCrossRef]: relação entre atividades e grupos.
 * - [ActivityMediaEntity]: mídias anexadas às atividades.
 * - [RankingEntryEntity]: entradas do ranking por grupo.
 *
 * A versão atual do banco é 1 porque ainda não há migrações entre versões.
 */
@Database(
    entities = [
        UserEntity::class,
        GroupEntity::class,
        StudyActivityEntity::class,
        ActivityGroupCrossRef::class,
        ActivityMediaEntity::class,
        RankingEntryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ProjectStudyDatabase : RoomDatabase() {

    /**
     * DAO responsável pelas operações relacionadas aos usuários.
     *
     * Usado para buscar o usuário atual, inserir dados iniciais e obter
     * informações de perfil usadas em telas como feed, ranking e perfil.
     */
    abstract fun userDao(): UserDao

    /**
     * DAO responsável pelas operações relacionadas aos grupos.
     *
     * Usado para observar grupos do usuário, atualizar progresso e buscar
     * informações do grupo principal exibido no app.
     */
    abstract fun groupDao(): GroupDao

    /**
     * DAO responsável pelas atividades de estudo.
     *
     * Usado para salvar sessões manuais, observar atividades por grupo ou usuário
     * e gerenciar relações com grupos e mídias.
     */
    abstract fun studyActivityDao(): StudyActivityDao

    /**
     * DAO responsável pelas entradas de ranking.
     *
     * Usado para observar o ranking de um grupo e atualizar posições após
     * a criação de novas sessões de estudo.
     */
    abstract fun rankingDao(): RankingDao
}