package com.mobile.sample.ui.main.dashboard.actions

import com.app3null.basestructure.actions.ViewStateAction
import com.mobile.sample.data.dataModels.game.GameInProgressData
import com.mobile.sample.ui.main.dashboard.screens.DashboardViewState

class SetGameInProgressAction(private val progressGames: List<GameInProgressData>) :
    ViewStateAction<DashboardViewState> {
    override fun newState(oldState: DashboardViewState): DashboardViewState {
        return oldState.copy(progressGames = progressGames)
    }
}