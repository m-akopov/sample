package com.mobile.sample.ui.main.dashboard.achievementsScreen.items

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app3null.basestructure.common.extensions.gone
import com.app3null.basestructure.common.extensions.visible
import com.app3null.common_code.extensions.setWidth
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.mobile.sample.R
import com.mobile.sample.databinding.ItemAchievementBinding
import com.mobile.sample.domain.entities.achievements.Achievement
import com.mobile.sample.domain.entities.achievements.AchievementState
import com.mobile.sample.other.dp
import com.mobile.sample.other.px

class AchievementItem(val achievement: Achievement, val pageCount: Int = 1) :
    AbstractBindingItem<ItemAchievementBinding>() {
    override val type: Int
        get() = R.id.item_achievement

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ItemAchievementBinding =
        ItemAchievementBinding.inflate(inflater, parent, false)

    override fun bindView(binding: ItemAchievementBinding, payloads: List<Any>) {
        with(binding) {
            root.setWidth(120.px)
            when (achievement.state) {
                AchievementState.NOT_STARTED -> {
                    groupCompleted.gone()
                    groupProgress.gone()
                    ivAchiev.alpha = 0.33f
                }
                AchievementState.IN_PROGRESS -> {
                    groupCompleted.gone()
                    groupProgress.visible()
                    ivAchiev.alpha = 1.0f
                    tvName.text = achievement.achievementData.name
                    progress.progress = achievement.progress
                    tvPecent.text =
                        tvPecent.context.getString(R.string.val_percent, achievement.progress)
                }
                AchievementState.COMPLETED -> {
                    groupCompleted.visible()
                    groupProgress.gone()
                    ivAchiev.alpha = 1.0f
                    tvNameBlack.text = achievement.achievementData.name
                }
            }

            Glide.with(ivAchiev)
                .load(achievement.achievementData.logo)
                .placeholder(R.drawable.ic_no_avatar)
                .into(ivAchiev)
        }
    }
}