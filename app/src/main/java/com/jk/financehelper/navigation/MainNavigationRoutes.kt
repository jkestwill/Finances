package com.jk.financehelper.navigation

import java.io.Serializable

data class MainNavigationRoutes(
    val route: String,
    val arguments: Set<Pair<String, Serializable>>?
)