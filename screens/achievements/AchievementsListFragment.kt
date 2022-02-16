package com.mobile.sample.ui.main.dashboard.achievementsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.app3null.basestructure.common.extensions.gone
import com.app3null.basestructure.common.extensions.notNull
import com.app3null.basestructure.common.extensions.visible
import com.app3null.basestructure.dialogs.Loader
import com.app3null.basestructure.fragments.BaseFragment
import com.app3null.common_code.extensions.showThrowableMessage
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.GenericItemAdapter
import com.mikepenz.fastadapter.dsl.genericFastAdapter
import com.mobile.sample.R
import com.mobile.sample.data.dataModels.enums.TypeOfTension
import com.mobile.sample.databinding.FragmentAchievementslistBinding
import com.mobile.sample.other.addNoDataView
import com.mobile.sample.ui.main.dashboard.achievementsScreen.items.AchievementGroupTitleItem
import com.mobile.sample.ui.main.dashboard.achievementsScreen.items.AchievementItem
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class AchievementsListFragment :
    BaseFragment<AchievementsListViewState, AchievementsListViewModel>() {

    val args: AchievementsListFragmentArgs by navArgs()
    private var _binding: FragmentAchievementslistBinding? = null
    private val binding get() = _binding!!

    private val itemsList: MutableList<GenericItemAdapter> = mutableListOf()
    var fastAdapter = FastAdapter.with(itemsList)

    private val viewModel: AchievementsListViewModel by viewModel {
        parametersOf(args.categoryId)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentAchievementslistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun provideViewModel(): AchievementsListViewModel {
        return viewModel
    }

    override fun reflectState(viewState: AchievementsListViewState) {
        viewState.showLoader?.getValue().notNull {
            with(binding){
                progressBar.visible()
                rvAchievements.gone()
            }
        }

        viewState.showError?.getValue().notNull {
            dismissAndClearDialogs()
            requireContext().showThrowableMessage(it)
            binding.progressBar.gone()
            binding.rvAchievements.gone()
        }

        viewState.showListAchievements?.getValue().notNull  { map ->
            itemsList.clear()
            map.keys.forEach { keyStr ->
                itemsList.add(GenericItemAdapter().apply {
                    add(AchievementGroupTitleItem(keyStr))
                })
                map[keyStr]?.forEach { achiv ->
                    itemsList.add(GenericItemAdapter().apply {
                        add(AchievementItem(achiv))
                    })
                }
            }
            fastAdapter = FastAdapter.with(itemsList)
            //fastAdapter.addAdapters(itemsList)
            fastAdapter.addNoDataView(binding.noDataView)
            binding.rvAchievements.adapter = fastAdapter
            //fastAdapter.notifyAdapterDataSetChanged()

            binding.progressBar.gone()
            binding.rvAchievements.visible()
            setupListeners()
        }

        viewState.showCategory?.getValue().notNull {
            with(binding) {
                tvToolbarTitle.text = it.achievementCategoryData.name
                tvCategoryDescription.text = it.achievementCategoryData.description
            }
        }

        viewState.selectTab?.getValue().notNull {
            when (it) {
                TypeOfTension.WEAK -> selectTab(binding.bWeak)
                TypeOfTension.MEDIUM -> selectTab(binding.bMedium)
                TypeOfTension.TIGHT -> selectTab(binding.bTight)
            }

        }
    }

    override fun renderView(savedInstanceState: Bundle?) {
        with(binding) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow)
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            setupRecycler()

        }
    }

    private fun setupRecycler() {
        fastAdapter.addNoDataView(binding.noDataView)
        val layoutManager = GridLayoutManager(requireContext(), 3)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (itemsList[position].getAdapterItem(0) is AchievementGroupTitleItem) 3 else 1
            }

        }
        binding.rvAchievements.layoutManager = layoutManager
        binding.rvAchievements.adapter = fastAdapter
    }

    private fun setupListeners() {
        fastAdapter.onClickListener = { _, _, item, _ ->
            if (item.type == R.id.item_achievement) {
//                AlertDialog.Builder(requireContext())
//                    .setTitle((item as AchievementItem).achievement.achievementData.name)
//                    .setPositiveButton(R.string.buttons_ok) { _, _ -> }
//                    .create().show()
                findNavController().navigate(
                    AchievementsListFragmentDirections
                        .actionAchievementsListFragmentToAchievementDetailsFragment(
                            (item as AchievementItem).achievement))
            }
            true
        }

        binding.bWeak.setOnClickListener {
            viewModel.currTension = TypeOfTension.WEAK
        }

        binding.bMedium.setOnClickListener {
            viewModel.currTension = TypeOfTension.MEDIUM
        }

        binding.bTight.setOnClickListener {
            viewModel.currTension = TypeOfTension.TIGHT
        }

    }

    private fun selectTab(selectedTabIndicator: View) {
        binding.bWeak.isSelected = false
        binding.bMedium.isSelected = false
        binding.bTight.isSelected = false

        selectedTabIndicator.isSelected = true

    }
}


