package com.mobile.sample.ui.main.dashboard.screens

import com.app3null.basestructure.models.DisposableValue
import com.mobile.sample.data.dataModels.enums.TypeOfTension
import com.mobile.sample.data.dataModels.game.GameInInvitationData
import com.mobile.sample.data.dataModels.game.GameInProgressData
import com.mobile.sample.data.dataModels.baseModels.GameModel
import com.mobile.sample.domain.entities.achievements.AchievementCategory

data class DashboardViewState(
    val toGame: DisposableValue<Long>? = null,
    val toAchievementsListScreen: DisposableValue<Long>? = null,
    val showLoader: DisposableValue<Boolean>? = null,
    val showConnectionStatus: DisposableValue<Boolean>? = null,
    val toTensionSettings: DisposableValue<Boolean>? = null,
    val toPlayScreen: DisposableValue<Boolean>? = null,
    val toInvitationsScreen: DisposableValue<Boolean>? = null,
    val showInvitations: List<GameModel>? = null,
    val showError: DisposableValue<Throwable>? = null,
    val progressGames: List<GameInProgressData>? = null,
    val goTo: DisposableValue<Boolean>? = null,
    val setTensionName: TypeOfTension? = null,
    val toSetInvitationScreen: DisposableValue<GameInInvitationData>? = null,
    val toGameScreen: DisposableValue<Long>? = null,
    val showListData: List<AchievementCategory>? = null,
    val showAchievementsCount: Int? = null
)
