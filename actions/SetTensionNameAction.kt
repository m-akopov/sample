package com.mobile.sample.ui.main.dashboard.actions

import com.app3null.basestructure.actions.ViewStateAction
import com.mobile.sample.data.dataModels.enums.TypeOfTension
import com.mobile.sample.ui.main.dashboard.screens.DashboardViewState

class SetTensionNameAction(private val type: TypeOfTension) : ViewStateAction<DashboardViewState> {
    override fun newState(oldState: DashboardViewState): DashboardViewState {
        return oldState.copy(setTensionName = type)
    }
}