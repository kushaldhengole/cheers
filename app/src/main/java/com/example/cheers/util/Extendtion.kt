package com.example.cheers.util

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections

object Extendtins {



    fun NavController.safeNavigate(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run {
            navigate(direction)
        }
    }
}