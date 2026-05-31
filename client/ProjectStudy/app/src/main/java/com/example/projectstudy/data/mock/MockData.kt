package com.example.projectstudy.data.mock

import com.example.projectstudy.domain.model.ActivityAuthor
import com.example.projectstudy.domain.model.Group
import com.example.projectstudy.domain.model.RankingEntry
import com.example.projectstudy.domain.model.RankingMetric
import com.example.projectstudy.domain.model.StudyActivity
import com.example.projectstudy.domain.model.User

object MockData {

    private const val ONE_HOUR = 60 * 60 * 1000L
    private const val ONE_DAY = 24 * ONE_HOUR

    private val now = System.currentTimeMillis()

    val users = listOf(
        User(
            id = "user_1",
            name = "Maria Clara",
            username = "@mariaclara",
            email = "maria@email.com",
            institution = "UFC",
            course = "Engenharia",
            avatarInitials = "MC",
            avatarUrl = "",
            streakDays = 9,
            totalMinutes = 5220,
            totalActivities = 38
        ),
        User(
            id = "user_2",
            name = "Rafael Lima",
            username = "@rafaellima",
            email = "rafael@email.com",
            institution = "UFC",
            course = "Física",
            avatarInitials = "RL",
            avatarUrl = "",
            streakDays = 5,
            totalMinutes = 3900,
            totalActivities = 27
        ),
        User(
            id = "user_3",
            name = "Lucas Silva",
            username = "@lucassilva",
            email = "lucas@email.com",
            institution = "UFC",
            course = "Ciência da Computação",
            avatarInitials = "LS",
            avatarUrl = "",
            streakDays = 3,
            totalMinutes = 2760,
            totalActivities = 21
        ),
        User(
            id = "user_4",
            name = "João Vitor",
            username = "@joaovitor",
            email = "joao@email.com",
            institution = "UFC",
            course = "Engenharia",
            avatarInitials = "JV",
            avatarUrl = "",
            streakDays = 7,
            totalMinutes = 5220,
            totalActivities = 32
        ),
        User(
            id = "user_5",
            name = "Ana Beatriz",
            username = "@anabeatriz",
            email = "ana@email.com",
            institution = "UFC",
            course = "Design Digital",
            avatarInitials = "AB",
            avatarUrl = "",
            streakDays = 12,
            totalMinutes = 6120,
            totalActivities = 44
        ),
        User(
            id = "user_6",
            name = "Pedro Henrique",
            username = "@pedrohenrique",
            email = "pedro@email.com",
            institution = "UFC",
            course = "Sistemas de Informação",
            avatarInitials = "PH",
            avatarUrl = "",
            streakDays = 4,
            totalMinutes = 3180,
            totalActivities = 19
        ),
        User(
            id = "user_7",
            name = "Larissa Souza",
            username = "@larissasouza",
            email = "larissa@email.com",
            institution = "UFC",
            course = "Engenharia",
            avatarInitials = "LS",
            avatarUrl = "",
            streakDays = 6,
            totalMinutes = 3540,
            totalActivities = 24
        ),
        User(
            id = "user_8",
            name = "Bruno Alves",
            username = "@brunoalves",
            email = "bruno@email.com",
            institution = "UFC",
            course = "Matemática",
            avatarInitials = "BA",
            avatarUrl = "",
            streakDays = 2,
            totalMinutes = 1980,
            totalActivities = 13
        )
    )

    private fun User.toAuthor(): ActivityAuthor {
        return ActivityAuthor(
            id = id,
            name = name,
            avatarInitials = avatarInitials,
            avatarUrl = avatarUrl
        )
    }

    private val mariaAuthor = users[0].toAuthor()
    private val rafaelAuthor = users[1].toAuthor()
    private val lucasAuthor = users[2].toAuthor()
    private val joaoAuthor = users[3].toAuthor()
    private val anaAuthor = users[4].toAuthor()
    private val pedroAuthor = users[5].toAuthor()
    private val larissaAuthor = users[6].toAuthor()
    private val brunoAuthor = users[7].toAuthor()

    val groups = listOf(
        Group(
            id = "group_1",
            name = "Galera da Eng.",
            description = "Grupo de estudos da turma de Engenharia.",
            bannerUrl = "https://images.unsplash.com/photo-1522202176988-66273c2fd55f",
            inviteCode = "LUMIO-X7B9",
            memberCount = 8,
            goalMinutes = 6000,
            currentMinutes = 4260,
            userRankingPosition = 3,
            userMinutes = 2040,
            rankingMetric = RankingMetric.TIME,
            createdAtMillis = now - (5 * ONE_DAY),
            isActive = true
        ),
        Group(
            id = "group_2",
            name = "Dev Friends",
            description = "Programação, APIs, mobile e revisão de código.",
            bannerUrl = "https://images.unsplash.com/photo-1516321318423-f06f85e504b3",
            inviteCode = "LUMIO-D3V1",
            memberCount = 5,
            goalMinutes = 3000,
            currentMinutes = 1680,
            userRankingPosition = 1,
            userMinutes = 720,
            rankingMetric = RankingMetric.DAYS,
            createdAtMillis = now - (8 * ONE_DAY),
            isActive = true
        ),
        Group(
            id = "group_3",
            name = "Reta Final",
            description = "Preparação para provas, listas e simulados.",
            bannerUrl = "https://images.unsplash.com/photo-1503676260728-1c00da094a0b",
            inviteCode = "LUMIO-R3T4",
            memberCount = 4,
            goalMinutes = 2400,
            currentMinutes = 900,
            userRankingPosition = 2,
            userMinutes = 420,
            rankingMetric = RankingMetric.TIME,
            createdAtMillis = now - (14 * ONE_DAY),
            isActive = false
        )
    )

    val activities = listOf(
        // Hoje - group_1
        StudyActivity(
            id = "activity_1",
            groupId = "group_1",
            author = mariaAuthor,
            title = "Derivadas parciais",
            subject = "Cálculo II",
            description = "Estudando derivadas parciais e regra da cadeia.",
            durationMinutes = 135,
            imageUrl = "https://images.unsplash.com/photo-1456513080510-7bf3a84b82f8",
            reactions = 4,
            createdAtMillis = now - (1 * ONE_HOUR),
            isManual = true
        ),
        StudyActivity(
            id = "activity_2",
            groupId = "group_1",
            author = rafaelAuthor,
            title = "Revisão de dinâmica",
            subject = "Física",
            description = "Revisando dinâmica e resolvendo exercícios.",
            durationMinutes = 90,
            imageUrl = "https://images.unsplash.com/photo-1516979187457-637abb4f9353",
            reactions = 1,
            createdAtMillis = now - (2 * ONE_HOUR),
            isManual = false
        ),
        StudyActivity(
            id = "activity_3",
            groupId = "group_1",
            author = joaoAuthor,
            title = "Lista de exercícios",
            subject = "Álgebra Linear",
            description = "Resolvendo exercícios de matrizes e determinantes.",
            durationMinutes = 82,
            imageUrl = "https://images.unsplash.com/photo-1497633762265-9d179a990aa6",
            reactions = 5,
            createdAtMillis = now - (4 * ONE_HOUR),
            isManual = true
        ),
        StudyActivity(
            id = "activity_4",
            groupId = "group_1",
            author = anaAuthor,
            title = "Resumo de mecânica",
            subject = "Física",
            description = "Organizando resumo para a prova de mecânica.",
            durationMinutes = 70,
            imageUrl = "https://images.unsplash.com/photo-1481627834876-b7833e8f5570",
            reactions = 3,
            createdAtMillis = now - (6 * ONE_HOUR),
            isManual = false
        ),

        // Ontem - group_1
        StudyActivity(
            id = "activity_5",
            groupId = "group_1",
            author = pedroAuthor,
            title = "Equações diferenciais",
            subject = "Cálculo III",
            description = "Estudando EDOs de primeira ordem.",
            durationMinutes = 120,
            imageUrl = "https://images.unsplash.com/photo-1434030216411-0b793f4b4173",
            reactions = 6,
            createdAtMillis = now - ONE_DAY - (2 * ONE_HOUR),
            isManual = true
        ),
        StudyActivity(
            id = "activity_6",
            groupId = "group_1",
            author = larissaAuthor,
            title = "Revisão de circuitos",
            subject = "Eletricidade",
            description = "Resolução de questões sobre resistores.",
            durationMinutes = 105,
            imageUrl = "https://images.unsplash.com/photo-1509062522246-3755977927d7",
            reactions = 2,
            createdAtMillis = now - ONE_DAY - (5 * ONE_HOUR),
            isManual = false
        ),
        StudyActivity(
            id = "activity_7",
            groupId = "group_1",
            author = brunoAuthor,
            title = "Integrais duplas",
            subject = "Cálculo II",
            description = "Praticando mudança de coordenadas.",
            durationMinutes = 65,
            imageUrl = "https://images.unsplash.com/photo-1509228468518-180dd4864904",
            reactions = 1,
            createdAtMillis = now - ONE_DAY - (8 * ONE_HOUR),
            isManual = true
        ),

        // 3 dias atrás - group_1
        StudyActivity(
            id = "activity_8",
            groupId = "group_1",
            author = mariaAuthor,
            title = "Geometria analítica",
            subject = "Matemática",
            description = "Retas, planos e distância entre pontos.",
            durationMinutes = 95,
            imageUrl = "https://images.unsplash.com/photo-1503676260728-1c00da094a0b",
            reactions = 4,
            createdAtMillis = now - (3 * ONE_DAY) - (3 * ONE_HOUR),
            isManual = false
        ),
        StudyActivity(
            id = "activity_9",
            groupId = "group_1",
            author = joaoAuthor,
            title = "PAA - Grafos",
            subject = "Algoritmos",
            description = "Revisando BFS, DFS e representação de grafos.",
            durationMinutes = 110,
            imageUrl = "https://images.unsplash.com/photo-1461749280684-dccba630e2f6",
            reactions = 7,
            createdAtMillis = now - (3 * ONE_DAY) - (6 * ONE_HOUR),
            isManual = true
        ),

        // group_2
        StudyActivity(
            id = "activity_10",
            groupId = "group_2",
            author = lucasAuthor,
            title = "Implementação da API REST",
            subject = "Programação",
            description = "Implementando endpoints principais do projeto.",
            durationMinutes = 45,
            imageUrl = "https://images.unsplash.com/photo-1461749280684-dccba630e2f6",
            reactions = 2,
            createdAtMillis = now - (3 * ONE_HOUR),
            isManual = false
        ),
        StudyActivity(
            id = "activity_11",
            groupId = "group_2",
            author = joaoAuthor,
            title = "Compose UI",
            subject = "Android",
            description = "Criando componentes reutilizáveis no Jetpack Compose.",
            durationMinutes = 75,
            imageUrl = "https://images.unsplash.com/photo-1516321318423-f06f85e504b3",
            reactions = 5,
            createdAtMillis = now - ONE_DAY - ONE_HOUR,
            isManual = false
        ),
        StudyActivity(
            id = "activity_12",
            groupId = "group_2",
            author = pedroAuthor,
            title = "Correção de bugs",
            subject = "Mobile",
            description = "Ajustando estados de loading e erro.",
            durationMinutes = 60,
            imageUrl = "https://images.unsplash.com/photo-1555066931-4365d14bab8c",
            reactions = 3,
            createdAtMillis = now - (2 * ONE_DAY),
            isManual = true
        ),

        // group_3
        StudyActivity(
            id = "activity_13",
            groupId = "group_3",
            author = anaAuthor,
            title = "Simulado de prova",
            subject = "Cálculo I",
            description = "Resolvendo questões antigas da disciplina.",
            durationMinutes = 150,
            imageUrl = "https://images.unsplash.com/photo-1519389950473-47ba0277781c",
            reactions = 8,
            createdAtMillis = now - (2 * ONE_DAY) - (4 * ONE_HOUR),
            isManual = true
        ),
        StudyActivity(
            id = "activity_14",
            groupId = "group_3",
            author = brunoAuthor,
            title = "Revisão de limites",
            subject = "Cálculo I",
            description = "Praticando limites laterais e continuidade.",
            durationMinutes = 80,
            imageUrl = "https://images.unsplash.com/photo-1513258496099-48168024aec0",
            reactions = 2,
            createdAtMillis = now - (4 * ONE_DAY),
            isManual = false
        )
    )

    val rankingGroupOne = listOf(
        RankingEntry(
            groupId = "group_1",
            user = users[0],
            position = 1,
            totalMinutes = 3120,
            activeDays = 5,
            isCurrentUser = false
        ),
        RankingEntry(
            groupId = "group_1",
            user = users[4],
            position = 2,
            totalMinutes = 2580,
            activeDays = 5,
            isCurrentUser = false
        ),
        RankingEntry(
            groupId = "group_1",
            user = users[3],
            position = 3,
            totalMinutes = 2040,
            activeDays = 3,
            isCurrentUser = true
        ),
        RankingEntry(
            groupId = "group_1",
            user = users[1],
            position = 4,
            totalMinutes = 1980,
            activeDays = 4,
            isCurrentUser = false
        ),
        RankingEntry(
            groupId = "group_1",
            user = users[6],
            position = 5,
            totalMinutes = 1740,
            activeDays = 3,
            isCurrentUser = false
        ),
        RankingEntry(
            groupId = "group_1",
            user = users[5],
            position = 6,
            totalMinutes = 1320,
            activeDays = 2,
            isCurrentUser = false
        ),
        RankingEntry(
            groupId = "group_1",
            user = users[7],
            position = 7,
            totalMinutes = 960,
            activeDays = 2,
            isCurrentUser = false
        ),
        RankingEntry(
            groupId = "group_1",
            user = users[2],
            position = 8,
            totalMinutes = 720,
            activeDays = 1,
            isCurrentUser = false
        )
    )

    val rankingGroupTwo = listOf(
        RankingEntry(
            groupId = "group_2",
            user = users[3],
            position = 1,
            totalMinutes = 720,
            activeDays = 4,
            isCurrentUser = true
        ),
        RankingEntry(
            groupId = "group_2",
            user = users[2],
            position = 2,
            totalMinutes = 660,
            activeDays = 3,
            isCurrentUser = false
        ),
        RankingEntry(
            groupId = "group_2",
            user = users[5],
            position = 3,
            totalMinutes = 540,
            activeDays = 3,
            isCurrentUser = false
        ),
        RankingEntry(
            groupId = "group_2",
            user = users[1],
            position = 4,
            totalMinutes = 480,
            activeDays = 2,
            isCurrentUser = false
        )
    )

    val rankingGroupThree = listOf(
        RankingEntry(
            groupId = "group_3",
            user = users[4],
            position = 1,
            totalMinutes = 900,
            activeDays = 2,
            isCurrentUser = false
        ),
        RankingEntry(
            groupId = "group_3",
            user = users[3],
            position = 2,
            totalMinutes = 420,
            activeDays = 1,
            isCurrentUser = true
        ),
        RankingEntry(
            groupId = "group_3",
            user = users[7],
            position = 3,
            totalMinutes = 360,
            activeDays = 1,
            isCurrentUser = false
        )
    )

    fun getRankingByGroupId(groupId: String): List<RankingEntry> {
        return when (groupId) {
            "group_1" -> rankingGroupOne
            "group_2" -> rankingGroupTwo
            "group_3" -> rankingGroupThree
            else -> emptyList()
        }
    }

    fun getActivitiesByGroupId(groupId: String): List<StudyActivity> {
        return activities
            .filter { activity ->
                activity.groupId == groupId
            }
            .sortedByDescending { activity ->
                activity.createdAtMillis
            }
    }

    fun getCurrentUser(): User {
        return users.first { user ->
            user.id == "user_4"
        }
    }

    fun getFirstUserGroup(): Group {
        return groups.first()
    }
}