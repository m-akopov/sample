
package com.mobile.sample.ui.main.dashboard.achievementsScreen.actions

import com.app3null.basestructure.actions.ViewStateAction
import com.app3null.basestructure.models.DisposableValue
import com.mobile.sample.ui.main.dashboard.achievementsScreen.AchievementsListViewState

class ShowLoaderAction() : ViewStateAction<AchievementsListViewState> {
    override fun newState(oldState: AchievementsListViewState): AchievementsListViewState {
        return oldState.copy(showLoader = DisposableValue(true))
    }
}

