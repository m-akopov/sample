package com.mobile.sample.domain.useCases.achievements

import com.mobile.sample.domain.repositoryes.AchievementsRepository

class GetLastAchievementsUseCase(private val repository: AchievementsRepository) {
    fun start(userId: Long, ropeTensionType: String) =
        repository.getLastAchievements(userId, ropeTensionType)
}