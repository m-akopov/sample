package com.mobile.sample.ui.main.dashboard.actions

import com.app3null.basestructure.actions.ViewStateAction
import com.app3null.basestructure.models.DisposableValue
import com.mobile.sample.ui.main.dashboard.screens.DashboardViewState

class ShowConnectionStatusAction(private val isConnected: Boolean) : ViewStateAction<DashboardViewState> {
    override fun newState(oldState: DashboardViewState): DashboardViewState {
        return oldState.copy(showConnectionStatus = DisposableValue(isConnected))
    }
}