package com.example.projectstudy.domain.model

/**
 * Lançada quando nenhum grupo sincronizado é encontrado no banco local do usuário.
 */
class NoUserGroupException(
    message: String = "Nenhum grupo sincronizado encontrado no banco local."
) : Exception(message)
