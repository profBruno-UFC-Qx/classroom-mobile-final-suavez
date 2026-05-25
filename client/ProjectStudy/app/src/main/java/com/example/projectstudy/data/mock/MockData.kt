package com.example.projectstudy.data.mock

import com.example.projectstudy.R
import com.example.projectstudy.domain.model.Activity
import com.example.projectstudy.domain.model.Group
import com.example.projectstudy.domain.model.User

object MockData {

    val currentUser = User(
        id = "1",
        name = "Henrique",
        username = "@henrique",
        streak = 12,
        totalHours = 34
    )

    val userGabriel = User(
        id = "2",
        name = "Gabriel",
        username = "@manogabs",
        streak = 5,
        totalHours = 20
    )

    val userLua = User(
        id = "3",
        name = "Lua",
        username = "@lua",
        streak = 8,
        totalHours = 25
    )

    val userIgor = User(
        id = "4",
        name = "Igor",
        username = "@igor",
        streak = 2,
        totalHours = 10
    )

    val mockFeed = listOf(
        Activity(
            id = "101",
            user = currentUser,
            subject = "Engenharia de Software",
            duration = "2h15",
            description = "Estruturando a arquitetura do MVP no Android Studio.",
            photoResId = R.drawable.foto_codigo,
            reactionsCount = 4
        ),
        Activity(
            id = "102",
            user = userGabriel,
            subject = "Programação Funcional",
            duration = "1h30",
            description = "Resolvendo listas de recursão em Haskell.",
            photoResId = R.drawable.foto_caderno,
            reactionsCount = 2
        ),
        Activity(
            id = "103",
            user = userLua,
            subject = "Cálculo II",
            duration = "45min",
            description = "Estudando derivadas parciais...",
            photoResId = R.drawable.foto_livro,
            reactionsCount = 0
        )
    )

    val mockGroup = Group(
        id = "G1",
        name = "Galera da Eng.",
        memberCount = 6,
        goalHours = 100,
        currentHours = 89,
        ranking = listOf(currentUser, userLua, userGabriel, userIgor)
    )
}