package com.mobile.sample.ui.main.dashboard.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.app3null.basestructure.common.extensions.gone
import com.app3null.basestructure.common.extensions.notNull
import com.app3null.basestructure.common.extensions.visible
import com.app3null.basestructure.dialogs.Loader
import com.app3null.basestructure.fragments.BaseFragment
import com.app3null.common_code.extensions.showThrowableMessage
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.GenericItemAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.diff.DiffCallback
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import com.mobile.sample.R
import com.mobile.sample.data.dataModels.enums.TypeOfGames
import com.mobile.sample.data.dataModels.enums.TypeOfStatusGame
import com.mobile.sample.data.dataModels.game.GameInInvitationData
import com.mobile.sample.databinding.FragmentDashboardBinding
import com.mobile.sample.other.addNoDataView
import com.mobile.sample.other.items.friends.FriendInvitesItem
import com.mobile.sample.ui.main.dashboard.achievementsScreen.items.AchievementGroupTitleItem
import com.mobile.sample.ui.main.dashboard.items.AchivCategoryItem
import com.mobile.sample.ui.main.play.items.GameInProgressHalfItem
import com.mobile.sample.ui.main.play.items.GameItem
import org.koin.core.parameter.parametersOf

class DashboardFragment : BaseFragment<DashboardViewState, DashboardViewModel>() {
    val args by navArgs<DashboardFragmentArgs>()
    private val itemsList: MutableList<GenericItemAdapter> = mutableListOf()
    var fastAdapter = FastAdapter.with(itemsList)

    private val invitesAdapter = ItemAdapter<FriendInvitesItem>()
    private val invitesFastAdapter = FastAdapter.with(invitesAdapter)

    private val inProgressItem = ItemAdapter<GameInProgressHalfItem>()
    private val inProgressItemFastAdapter = FastAdapter.with(inProgressItem)


    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModel() {
        parametersOf(args.gameId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun provideViewModel(): DashboardViewModel {
        return viewModel
    }

    override fun reflectState(viewState: DashboardViewState) {
        viewState.showLoader?.getValue().notNull {
            registerDialog(Loader.show(requireContext()))
        }

        viewState.showError?.getValue().notNull {
            dismissAndClearDialogs()
            requireContext().showThrowableMessage(it)
        }

        viewState.toPlayScreen?.getValue().notNull {
            findNavController().navigate(R.id.playFragment)
        }

        viewState.toInvitationsScreen?.getValue().notNull {
            findNavController().navigate(R.id.invitationsFragment)
        }

        viewState.toSetInvitationScreen?.getValue().notNull {
            findNavController().navigate(
                DashboardFragmentDirections.actionMeFragmentToSetInvitationFragment(
                    it
                )
            )
        }

        viewState.toGameScreen?.getValue().notNull {
            findNavController().navigate(
                DashboardFragmentDirections.actionMeFragmentToGameActivity(
                    it
                )
            )
        }


        viewState.progressGames?.let { list ->

            with(binding) {
                if (list.isEmpty()) {
                    rvInProgress.gone()
                    tvInProgressDesc.gone()
                } else {
                    rvInProgress.visible()
                    tvInProgressDesc.visible()
                }
            }

            FastAdapterDiffUtil[inProgressItem] = FastAdapterDiffUtil.calculateDiff(
                inProgressItem,
                list.map { GameInProgressHalfItem(it) },
                object : DiffCallback<GameInProgressHalfItem> {
                    override fun areContentsTheSame(
                        oldItem: GameInProgressHalfItem,
                        newItem: GameInProgressHalfItem
                    ): Boolean  = oldItem.model.hits == newItem.model.hits

                    override fun areItemsTheSame(
                        oldItem: GameInProgressHalfItem,
                        newItem: GameInProgressHalfItem
                    ): Boolean = oldItem.model.gameId == newItem.model.gameId

                    override fun getChangePayload(
                        oldItem: GameInProgressHalfItem,
                        oldItemPosition: Int,
                        newItem: GameInProgressHalfItem,
                        newItemPosition: Int
                    ): Any? = null
                }
            )
        }

        viewState.showInvitations.notNull { list ->
            invitesAdapter.clear()
            if (list.size > 3) {
                (0..2).map {
                    invitesAdapter.add(FriendInvitesItem(list[it]))
                }
            } else {
                list.map {
                    invitesAdapter.add(FriendInvitesItem(it))
                }
            }
            showPlaceholder(list.isEmpty())
        }

        viewState.showListData?.let { list ->
            if (itemsList.isEmpty()) {
                itemsList.add(GenericItemAdapter().apply {
                    add(AchievementGroupTitleItem("Achievements for you"))
                })
                list.forEach {
                    itemsList.add(GenericItemAdapter().apply {
                        add(AchivCategoryItem(it))
                    })
                }
                fastAdapter.addAdapters(itemsList)
                fastAdapter.notifyAdapterDataSetChanged()
            }
        }

        viewState.toAchievementsListScreen?.getValue().notNull {
            findNavController().navigate(
                DashboardFragmentDirections.actionMeFragmentToAchievementsListFragment(
                    it
                )
            )
        }

        viewState.toGame?.getValue().notNull { gameId ->
            findNavController().navigate(R.id.gameActivity, Bundle().also {
                it.putLong("game_id", gameId)
            })
        }

        viewState.toTensionSettings?.getValue().notNull {
            findNavController().navigate(DashboardFragmentDirections.actionMeFragmentToSetTensionActivity())
        }

        viewState.setTensionName?.notNull {
            binding.toolBarWithSnackBar.setTension(it)
        }

        viewState.showConnectionStatus?.getValue().notNull {
            if (it) {
                binding.toolBarWithSnackBar.showWarningMessage(
                    resources.getString(R.string.connected),
                    resources.getString(R.string.close)
                ) {
                    binding.toolBarWithSnackBar.hideMessage()
                    viewModel.closedConnectionWarningStatus(true)
                }
            } else {
                binding.toolBarWithSnackBar.showErrorMessage(
                    resources.getString(R.string.disconnected),
                    resources.getString(R.string.settings)
                ) {
                    //viewModel.closedConnectionWarningStatus(false)
                    findNavController().navigate(R.id.connectDevicesDialogFragment)
                }
            }
        }

        viewState.showAchievementsCount?.let {
             binding.toolBarWithSnackBar.showCounter(it)
        }
    }

    override fun renderView(savedInstanceState: Bundle?) {
        fastAdapter.addNoDataView(binding.noDataView)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = fastAdapter
        setupViewPager()
        setupRecyclerView()
        setupListeners()
    }

    private fun setupViewPager() {
        with(binding) {
            rvInvites.clipToPadding = false
            rvInvites.adapter = invitesFastAdapter

            rvInvites.offscreenPageLimit = 2

            val pageTranslationX = requireContext().resources.getDimension(R.dimen.dimen_16dp)
            val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
                page.translationX = -pageTranslationX * position
            }
            rvInvites.setPageTransformer(pageTransformer)
            val itemDecoration = GameItem.HorizontalMarginItemDecoration(
                requireContext(),
                R.dimen.dimen_5dp
            )
            rvInvites.addItemDecoration(itemDecoration)
        }
    }

    private fun setupRecyclerView() {
        with(binding) {
            val mLayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvInProgress.layoutManager = mLayoutManager
            rvInProgress.adapter = inProgressItemFastAdapter
        }

    }

    private fun setupListeners() {
        with(binding) {
            fastAdapter.onClickListener = { view, adapter, item, position ->
                if (item.type == R.id.id_achievement_category) {
                    viewModel.categoryClicked((item as AchivCategoryItem).achievementCategory.achievementCategoryData.id!!)
                    true
                } else {
                    false
                }
            }

            btnExplore.setOnClickListener {
                viewModel.toPlayScreen()
            }

            ivSeeAll.setOnClickListener {
                viewModel.toInvitationsScreen()
            }

            tvSeeAll.setOnClickListener {
                viewModel.toInvitationsScreen()
            }

            invitesFastAdapter.addEventHook(FriendInvitesItem.FriendInvitesItemClickEvent { _, item ->
                val model = GameInInvitationData(
                    gameType = TypeOfGames.getTypeByName(item.gameTypeName),
                    userName = item.againstUser?.nickname,
                    userAvatar = item.againstUser?.profileImage,
                    hits = item.targetHits!!,
                    ropeTensionType = item.ropeTensionType,
                    gameId = item.id!!,
                    gameStatus = TypeOfStatusGame.getTypeByName(item.gameStatusName)
                )
                viewModel.toSetInvitationScreen(model)
            })



            toolBarWithSnackBar.setOnTensionClickListener {
                viewModel.tensionClicked()
            }
            toolBarWithSnackBar.setOnClickListener {
                viewModel.categoryClicked(0)
            }
        }

        inProgressItemFastAdapter.addEventHook(GameInProgressHalfItem.GameInProgressHalfItemClickEvent { _, item ->
            viewModel.toGameScreen(item.gameId)
        })

    }

    fun showPlaceholder(isEmpty: Boolean) {
        with(binding) {
            if (isEmpty) {
                rvInvites.gone()
                groupEmptyGamesPlaceholder.visible()
            } else {
                rvInvites.visible()
                groupEmptyGamesPlaceholder.gone()
            }
        }

    }
}
