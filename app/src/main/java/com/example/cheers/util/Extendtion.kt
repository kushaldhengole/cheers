package com.example.cheers.util

import androidx.navigation.NavController
import androidx.navigation.NavDirections

object Extendtion {



    fun NavController.safeNavigate(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run {
            navigate(direction)
        }
    }
}