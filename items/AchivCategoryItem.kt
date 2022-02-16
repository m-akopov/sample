package com.mobile.sample.ui.main.dashboard.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.mikepenz.fastadapter.items.AbstractItem
import com.mobile.sample.R
import com.mobile.sample.databinding.ItemAchievementCategoryBinding
import com.mobile.sample.domain.entities.achievements.AchievementCategory

class AchivCategoryItem(val achievementCategory: AchievementCategory) :
    AbstractBindingItem<ItemAchievementCategoryBinding>() {

    override fun bindView(binding: ItemAchievementCategoryBinding, payloads: List<Any>) {
        with(binding) {
            tvTitle.text = achievementCategory.achievementCategoryData.name
            tvDescription.text = achievementCategory.achievementCategoryData.description
            tvProgress.text =
                "${achievementCategory.achievementCategoryData.completedAchievements}/${achievementCategory.achievementCategoryData.totalAchievements}"
            ivAchievement.setImageURI(achievementCategory.achievementCategoryData.logo)
            progressBar.progress = achievementCategory.progress
        }
    }

    override val type: Int
        get() = R.id.id_achievement_category

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ItemAchievementCategoryBinding =
        ItemAchievementCategoryBinding.inflate(inflater,parent,false)
}