package com.mobile.sample.domain.useCases.achievements

import com.app3null.basestructure.common.extensions.async
import com.app3null.basestructure.usecases.BaseUseCase
import com.mobile.sample.domain.entities.achievements.Achievement
import com.mobile.sample.domain.repositoryes.AchievementsRepository
import io.reactivex.Observable

class GetAchievementsGroupsByIdUseCase(private val repository: AchievementsRepository) :
    BaseUseCase<Long, Observable<Map<String, List<Achievement>>>> {
    override fun start(arg: Long?): Observable<Map<String, List<Achievement>>> {
        TODO("Not used")
    }

    fun getAchievementGroupListById(
        id: Long,
        tension: String
    ): Observable<Map<String, List<Achievement>>> =
        repository.getAchievementGroupListById(id, tension)
            .flatMap { listData ->
                val map = mutableMapOf<String, List<Achievement>>()
                listData.forEach { data ->
                    map[data.name!!] = data.achievements?.map {
                        Achievement(it)
                    }!!
                }
                Observable.just(map.toMap())
            }
            .async()
}