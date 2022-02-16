
package com.mobile.sample.ui.main.dashboard.achievementDetail.actions

import com.app3null.basestructure.actions.ViewStateAction
import com.app3null.basestructure.models.DisposableValue
import com.mobile.sample.ui.main.dashboard.achievementDetail.AchievementDetailsViewState

class ToNextScreenAction : ViewStateAction<AchievementDetailsViewState> {
    override fun newState(oldState: AchievementDetailsViewState): AchievementDetailsViewState {
        return oldState.copy(toNextScreen = DisposableValue(true))
    }
}

