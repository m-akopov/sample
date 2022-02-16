package com.mobile.sample.ui.main.dashboard.achievementsScreen.actions

import com.app3null.basestructure.actions.ViewStateAction
import com.app3null.basestructure.models.DisposableValue
import com.mobile.sample.data.dataModels.enums.TypeOfTension
import com.mobile.sample.ui.main.dashboard.achievementsScreen.AchievementsListViewState

class ShowSelectedTab(private val type: TypeOfTension) :
    ViewStateAction<AchievementsListViewState> {
    override fun newState(oldState: AchievementsListViewState): AchievementsListViewState {
        return oldState.copy(selectTab = DisposableValue(type))
    }
}