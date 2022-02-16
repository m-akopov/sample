package com.mobile.sample.ui.main.dashboard.actions

import com.app3null.basestructure.actions.ViewStateAction
import com.mobile.sample.data.dataModels.baseModels.GameModel
import com.mobile.sample.ui.main.dashboard.screens.DashboardViewState

class ShowInvitationsAction(private val list:List<GameModel>) : ViewStateAction<DashboardViewState> {
    override fun newState(oldState: DashboardViewState): DashboardViewState {
        return oldState.copy(showInvitations = list)    }
}