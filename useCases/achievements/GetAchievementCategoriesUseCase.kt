package com.mobile.sample.domain.useCases.achievements

import com.app3null.basestructure.common.extensions.async
import com.app3null.basestructure.usecases.BaseUseCase
import com.mobile.sample.domain.entities.achievements.AchievementCategory
import com.mobile.sample.domain.repositoryes.AchievementsRepository
import io.reactivex.Observable
import kotlin.math.roundToInt

class GetAchievementCategoriesUseCase(private val repository: AchievementsRepository) :
    BaseUseCase<Any, Observable<List<AchievementCategory>>> {
    override fun start(arg: Any?): Observable<List<AchievementCategory>> =
        repository.getAchievementsCategoriesList()
            .map { list ->
                list.map {
                    AchievementCategory(it)
                }
            }.async()
}