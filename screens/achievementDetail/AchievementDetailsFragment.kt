package com.mobile.sample.ui.main.dashboard.achievementDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.app3null.basestructure.common.extensions.notNull
import com.app3null.basestructure.dialogs.Loader
import com.app3null.common_code.extensions.showThrowableMessage
import com.mobile.sample.R
import com.mobile.sample.databinding.FragmentAchievementdetailsBinding
import com.mobile.sample.domain.entities.achievements.AchievementState
import com.mobile.sample.other.BaseBottomSheetDialogFragment
import com.mobile.sample.other.CartType
import org.koin.android.viewmodel.ext.android.viewModel


class AchievementDetailsFragment :
    BaseBottomSheetDialogFragment<AchievementDetailsViewState, AchievementDetailsViewModel>() {

    private val args by navArgs<AchievementDetailsFragmentArgs>()
    private var _binding: FragmentAchievementdetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AchievementDetailsViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentAchievementdetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun provideViewModel(): AchievementDetailsViewModel {
        return viewModel
    }

    override fun reflectState(viewState: AchievementDetailsViewState) {
        viewState.showLoader?.getValue().notNull {
            registerDialog(Loader.show(requireContext()))
        }

        viewState.showError?.getValue().notNull {
            dismissAndClearDialogs()
            requireContext().showThrowableMessage(it)
        }

        viewState.toNextScreen?.getValue().notNull {
            dismissAndClearDialogs()
            //findNavController().navigate(R.id.)
        }
    }

    override fun renderView(savedInstanceState: Bundle?) {
        setupFullHeight()
        with(binding) {
            progressAchievement.setImageURI(args.achievement.achievementData.logo)
            progressAchievement.setProgress(args.achievement.progress)
            tvAchievTitle.text = args.achievement.achievementData.longTitle
            tvAchivDescription.text = args.achievement.achievementData.description
            tvCurrValue.text = getValueByUnit(
                args.achievement.achievementData.unit,
                args.achievement.achievementData.currentProgress
            )
            tvTotalValue.text = getValueByUnit(
                args.achievement.achievementData.unit,
                args.achievement.achievementData.targetAmount
            )
            when (args.achievement.state) {
                AchievementState.NOT_STARTED -> {
                    progressAchievement.alpha = 0.33f
                }
                AchievementState.IN_PROGRESS, AchievementState.COMPLETED -> {
                    progressAchievement.alpha = 1.0f
                }
            }
            btnAction.text = getButtonNameByType(args.achievement.achievementData.buttonActionType)
            btnAction.setOnClickListener {
                when (args.achievement.achievementData.buttonActionType) {
                    CartType.game -> findNavController().navigate(R.id.playFragment)
                    CartType.play -> findNavController().navigate(R.id.playFragment)
                    CartType.friends -> findNavController().navigate(R.id.playFragment) //todo
                }
            }
        }
    }

    private fun getButtonNameByType(buttonActionType: String?): String =
        when (buttonActionType) {
            CartType.game -> getString(R.string.go_to_games)
            CartType.play -> getString(R.string.invite_friends_to_play)
            CartType.friends -> getString(R.string.invite_friends)
            else -> ""
        }

    private fun getValueByUnit(unit: String?, value: Int?): String =
        when (unit) {
            "hours" -> {
                if (value?.compareTo(3600) ?: 0 < 0)
                    getString(R.string.val_min, value?.div(60))
                else
                    getString(R.string.val_hour, value?.div(3600))
            }
            "wins" -> getString(R.string.val_wins, value)
            "wins in a row" -> getString(R.string.val_wins_in_row, value)
            "games" -> getString(R.string.val_games, value)
            "friends" -> getString(R.string.val_friends, value)
            "power" -> getString(R.string.val_power, value)
            "hits" -> getString(R.string.val_hits, value)
            else -> ""
        }
}

