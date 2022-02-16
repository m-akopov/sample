package com.mobile.sample.ui.main.dashboard.achievementsScreen

import android.content.Context
import com.app3null.basestructure.viewModels.BaseViewModel
import com.mobile.sample.R
import com.mobile.sample.data.dataModels.acievements.AchievementCategoryData
import com.mobile.sample.data.dataModels.enums.TypeOfTension
import com.mobile.sample.domain.entities.achievements.AchievementCategory
import com.mobile.sample.domain.useCases.achievements.GetAchievementCategoryByIdUseCase
import com.mobile.sample.domain.useCases.achievements.GetAchievementsGroupsByIdUseCase
import com.mobile.sample.other.dispatchAction
import com.mobile.sample.ui.main.dashboard.achievementsScreen.actions.*

class AchievementsListViewModel(
    private val categoryId: Int,
    private val getAchievementsGroupsByIdUseCase: GetAchievementsGroupsByIdUseCase,
    getAchievementCategoryByIdUseCase: GetAchievementCategoryByIdUseCase,
    private val context:Context
) : BaseViewModel<AchievementsListViewState>() {

    override fun getInitialState(): AchievementsListViewState {
        return AchievementsListViewState()
    }

    var currTension = TypeOfTension.WEAK
        set(value) {
            field = value
            getAchievementsData()
        }

    init {
        getAchievementCategoryByIdUseCase.start(categoryId.toLong())
            .subscribe ({
                ShowCategoryAction(it).dispatchAction(this)
            },{
                ShowErrorAction(it).dispatchAction(this)
            })
            .also { registerDisposables(it) }

        getAchievementsData()
    }

    private fun getAchievementsData() {
        ShowSelectedTab(currTension).dispatchAction(this)
        ShowLoaderAction().dispatchAction(this)
        getAchievementsGroupsByIdUseCase
            .getAchievementGroupListById(categoryId.toLong(), currTension.backName)
            .subscribe({
                ShowListAchievements(it).dispatchAction(this)
            }, {
                ShowErrorAction(it).dispatchAction(this)
            })
            .also { registerDisposables(it) }
    }
}


