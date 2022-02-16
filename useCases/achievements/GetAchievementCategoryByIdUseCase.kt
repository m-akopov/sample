package com.mobile.sample.domain.useCases.achievements

import com.app3null.basestructure.usecases.BaseUseCase
import com.mobile.sample.data.dataModels.acievements.AchievementCategoryData
import com.mobile.sample.domain.entities.achievements.AchievementCategory
import com.mobile.sample.domain.repositoryes.AchievementsRepository
import io.reactivex.Completable
import io.reactivex.Observable

class GetAchievementCategoryByIdUseCase(private val repository: AchievementsRepository) :
    BaseUseCase<Long, Observable<AchievementCategory>> {
    override fun start(arg: Long?): Observable<AchievementCategory> =
        repository.getAchievementsCategoryById(arg!!)
            .map {
                AchievementCategory(it)
            }
}