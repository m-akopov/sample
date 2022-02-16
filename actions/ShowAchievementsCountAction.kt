package com.mobile.sample.ui.main.dashboard.actions

import com.app3null.basestructure.actions.ViewStateAction
import com.mobile.sample.ui.main.dashboard.screens.DashboardViewState

class ShowAchievementsCountAction(private val achievementCount: Int) : ViewStateAction<DashboardViewState> {
    override fun newState(oldState: DashboardViewState): DashboardViewState {
        return oldState.copy(showAchievementsCount = achievementCount)
    }
}