package com.mobile.sample.ui.main.dashboard.achievementsScreen.actions

import com.app3null.basestructure.actions.ViewStateAction
import com.app3null.basestructure.models.DisposableValue
import com.mobile.sample.ui.main.dashboard.screens.DashboardViewState

class ToGameAction(private val gameId: Long) : ViewStateAction<DashboardViewState> {
    override fun newState(oldState: DashboardViewState): DashboardViewState {
        return oldState.copy(toGame = DisposableValue(gameId))
    }
}