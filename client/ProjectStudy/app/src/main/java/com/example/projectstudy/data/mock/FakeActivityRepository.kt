package com.example.projectstudy.data.mock

import com.example.projectstudy.domain.model.StudyActivity
import com.example.projectstudy.domain.repository.ActivityRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeActivityRepository @Inject constructor() : ActivityRepository {

    override suspend fun getFeedActivities(): List<StudyActivity> {

        delay(1000)

        return listOf(
            StudyActivity(
                id = "1",
                title = "Cálculo II",
                description = "Estudando derivadas parciais.",
                duration = "2h15",
                userName = "Maria Clara",
                userAvatar = "MC",
                imageUrl = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fimg.freepik.com%2Ffotos-gratis%2Fmulher-bonita-em-pe-perto-de-uma-arvore_23-2148348874.jpg%3Fsize%3D626%26ext%3Djpg&f=1&nofb=1&ipt=b08118c32336ce9c0aa3abbc16673b1dafa2fe6f4cfb6cce12bd253b6ae6ef0a",
                reactions = 4,
                createdAt = "09:30"
            ),
            StudyActivity(
                id = "2",
                title = "Física",
                description = "Revisando dinâmica.",
                duration = "1h30",
                userName = "Rafael",
                userAvatar = "RL",
                imageUrl = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fimg.freepik.com%2Ffotos-gratis%2Fmulher-bonita-em-pe-perto-de-uma-arvore_23-2148348874.jpg%3Fsize%3D626%26ext%3Djpg&f=1&nofb=1&ipt=b08118c32336ce9c0aa3abbc16673b1dafa2fe6f4cfb6cce12bd253b6ae6ef0a",
                reactions = 2,
                createdAt = "08:10"
            ),
            StudyActivity(
                id = "3",
                title = "Programação",
                description = "Implementando a API REST.",
                duration = "45min",
                userName = "João",
                userAvatar = "JV",
                imageUrl = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fimg.freepik.com%2Ffotos-gratis%2Fmulher-bonita-em-pe-perto-de-uma-arvore_23-2148348874.jpg%3Fsize%3D626%26ext%3Djpg&f=1&nofb=1&ipt=b08118c32336ce9c0aa3abbc16673b1dafa2fe6f4cfb6cce12bd253b6ae6ef0a",
                reactions = 5,
                createdAt = "07:20"
            )
        )
    }
}