package com.mobile.sample.ui.main.dashboard.repositories

import com.mobile.sample.data.dataModels.acievements.AchievementCategoryData
import com.mobile.sample.data.dataModels.acievements.AchievementData
import com.mobile.sample.data.dataModels.acievements.AchievementGroupData
import io.reactivex.Observable
import io.reactivex.Single

interface AchievementsRepository {
    fun getAchievementsCategoriesList(): Observable<List<AchievementCategoryData>>
    fun getAchievementsCategoryById(id: Long): Observable<AchievementCategoryData>
    fun getAchievementGroupListById(id: Long,ropeTension:String): Observable<List<AchievementGroupData>>
    fun getLastAchievements(userId:Long,ropeTensionType:String): Single<List<AchievementData>>
}