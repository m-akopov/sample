package com.mobile.sample.ui.main.dashboard.actions

import com.app3null.basestructure.actions.ViewStateAction
import com.mobile.sample.domain.entities.achievements.AchievementCategory
import com.mobile.sample.ui.main.dashboard.screens.DashboardViewState

class ShowListDataAction(private val dataList: List<AchievementCategory>) : ViewStateAction<DashboardViewState> {
    override fun newState(oldState: DashboardViewState): DashboardViewState {
        return oldState.copy(showListData = dataList)
    }
}