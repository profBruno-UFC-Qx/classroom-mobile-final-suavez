package com.example.projectstudy.data.mock

import com.example.projectstudy.domain.model.Group
import com.example.projectstudy.domain.model.RankingEntry
import com.example.projectstudy.domain.model.RankingMetric
import com.example.projectstudy.domain.model.StudyActivity
import com.example.projectstudy.domain.model.User
import com.example.projectstudy.domain.model.ActivityAuthor

object MockData {

    val users = listOf(
        User(
            id = "user_1",
            name = "Maria Clara",
            username = "@mariaclara",
            email = "maria@email.com",
            institution = "UFC",
            course = "Engenharia",
            avatarInitials = "MC",
            avatarUrl = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fm.media-amazon.com%2Fimages%2FM%2FMV5BMTY2ODQ3NjMyMl5BMl5BanBnXkFtZTcwODg0MTUzNA%40%40._V1_.jpg&f=1&nofb=1&ipt=d0d3e1507889f06aba2c4c6557ea53e4ced9693ec2df39f70d8c92da777d01bf",
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
            avatarUrl = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fimg.freepik.com%2Fvetores-premium%2Fdesign-de-avatar-de-pessoa_24877-38137.jpg%3Fw%3D2000&f=1&nofb=1&ipt=39fee9a9d5412d74565e03620904b53c78258c4c5be5420a082e767011c59594",
            streakDays = 7,
            totalMinutes = 5220,
            totalActivities = 32
        )
    )

    private val mariaAuthor = ActivityAuthor(
        id = users[0].id,
        name = users[0].name,
        avatarInitials = users[0].avatarInitials,
        avatarUrl = users[0].avatarUrl
    )

    private val rafaelAuthor = ActivityAuthor(
        id = users[1].id,
        name = users[1].name,
        avatarInitials = users[1].avatarInitials,
        avatarUrl = users[1].avatarUrl
    )

    private val lucasAuthor = ActivityAuthor(
        id = users[2].id,
        name = users[2].name,
        avatarInitials = users[2].avatarInitials,
        avatarUrl = users[2].avatarUrl
    )

    private val joaoAuthor = ActivityAuthor(
        id = users[3].id,
        name = users[3].name,
        avatarInitials = users[3].avatarInitials,
        avatarUrl = users[3].avatarUrl
    )

    val groups = listOf(
        Group(
            id = "group_1",
            name = "Galera da Eng.",
            description = "Grupo de estudos da turma de Engenharia.",
            bannerUrl = "https://images.unsplash.com/photo-1522202176988-66273c2fd55f",
            inviteCode = "LUMIO-X7B9",
            memberCount = 6,
            goalMinutes = 6000,
            currentMinutes = 2040,
            userRankingPosition = 3,
            userMinutes = 2040,
            rankingMetric = RankingMetric.TIME,
            createdAtMillis = System.currentTimeMillis() - 86400000L,
            isActive = true
        ),
        Group(
            id = "group_2",
            name = "Dev Friends",
            description = "Grupo para estudar programação, APIs e mobile.",
            bannerUrl = "https://images.unsplash.com/photo-1516321318423-f06f85e504b3",
            inviteCode = "LUMIO-D3V1",
            memberCount = 4,
            goalMinutes = 3000,
            currentMinutes = 720,
            userRankingPosition = 1,
            userMinutes = 720,
            rankingMetric = RankingMetric.DAYS,
            createdAtMillis = System.currentTimeMillis() - 172800000L,
            isActive = false
        )
    )

    val activities = listOf(
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
            createdAtMillis = System.currentTimeMillis() - 3600000L,
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
            createdAtMillis = System.currentTimeMillis() - 7200000L,
            isManual = false
        ),
        StudyActivity(
            id = "activity_3",
            groupId = "group_2",
            author = lucasAuthor,
            title = "Implementação da API REST",
            subject = "Programação",
            description = "Implementando a API REST do projeto.",
            durationMinutes = 45,
            imageUrl = "https://images.unsplash.com/photo-1461749280684-dccba630e2f6",
            reactions = 2,
            createdAtMillis = System.currentTimeMillis() - 10800000L,
            isManual = false
        ),
        StudyActivity(
            id = "activity_4",
            groupId = "group_1",
            author = joaoAuthor,
            title = "Lista de exercícios",
            subject = "Álgebra Linear",
            description = "Resolvendo exercícios de matrizes e determinantes.",
            durationMinutes = 82,
            imageUrl = "https://images.unsplash.com/photo-1497633762265-9d179a990aa6",
            reactions = 5,
            createdAtMillis = System.currentTimeMillis() - 14400000L,
            isManual = true
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
            user = users[1],
            position = 2,
            totalMinutes = 2400,
            activeDays = 4,
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
            user = users[2],
            position = 4,
            totalMinutes = 1500,
            activeDays = 2,
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
            user = users[1],
            position = 3,
            totalMinutes = 480,
            activeDays = 2,
            isCurrentUser = false
        )
    )

    fun getRankingByGroupId(groupId: String): List<RankingEntry> {
        return when (groupId) {
            "group_1" -> rankingGroupOne
            "group_2" -> rankingGroupTwo
            else -> emptyList()
        }
    }

    fun getActivitiesByGroupId(groupId: String): List<StudyActivity> {
        return activities.filter { activity ->
            activity.groupId == groupId
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