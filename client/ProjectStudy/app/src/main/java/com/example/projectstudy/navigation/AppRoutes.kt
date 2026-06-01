package com.example.projectstudy.navigation

object AppRoutes {
    const val MANUAL_SESSION = "manual_session"
    const val MANUAL_SESSION_WITH_GROUP = "manual_session/{groupId}"

    fun manualSession(
        groupId: String
    ): String {
        return "manual_session/$groupId"
    }
}