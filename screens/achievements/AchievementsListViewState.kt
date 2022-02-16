package com.mobile.sample.ui.main.dashboard.achievementsScreen

import com.app3null.basestructure.models.DisposableValue
import com.mobile.sample.data.dataModels.enums.TypeOfTension
import com.mobile.sample.domain.entities.achievements.Achievement
import com.mobile.sample.domain.entities.achievements.AchievementCategory


data class AchievementsListViewState(
    val showCategory: DisposableValue<AchievementCategory>? = null,
    val showListAchievements: DisposableValue<Map<String, List<Achievement>>>? = null,
    val showLoader: DisposableValue<Boolean>? = null,
    val showError: DisposableValue<Throwable>? = null,
    val selectTab: DisposableValue<TypeOfTension>? = null
)

