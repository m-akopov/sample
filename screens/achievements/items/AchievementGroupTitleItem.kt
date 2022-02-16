package com.mobile.sample.ui.main.dashboard.achievementsScreen.items

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.mobile.sample.R
import com.mobile.sample.databinding.ItemAchievementGroupTitleBinding

class AchievementGroupTitleItem(val title:String) : AbstractBindingItem<ItemAchievementGroupTitleBinding>() {
    override val type: Int
        get() = R.id.item_achievement_title

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ItemAchievementGroupTitleBinding =
        ItemAchievementGroupTitleBinding.inflate(inflater,parent,false)

    override fun bindView(binding: ItemAchievementGroupTitleBinding, payloads: List<Any>) {
        binding.tvTitle.text = title
    }
}