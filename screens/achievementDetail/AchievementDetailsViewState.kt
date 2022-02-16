
package com.mobile.sample.ui.main.dashboard.achievementDetail

import com.app3null.basestructure.models.DisposableValue


data class AchievementDetailsViewState(
    val showLoader: DisposableValue<Boolean>? = null,
    val toNextScreen: DisposableValue<Boolean>? = null,
    val showError: DisposableValue<Throwable>? = null
)

