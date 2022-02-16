package com.mobile.sample.ui.main.dashboard.screens

import com.app3null.basestructure.common.extensions.async
import com.app3null.basestructure.viewModels.BaseViewModel
import com.mobile.sample.data.dataModels.enums.TypeOfGameMode
import com.mobile.sample.data.dataModels.enums.TypeOfStatusGame
import com.mobile.sample.data.dataModels.enums.TypeOfTension
import com.mobile.sample.data.dataModels.game.GameInInvitationData
import com.mobile.sample.domain.useCases.achievements.GetAchievementCategoriesUseCase
import com.mobile.sample.domain.useCases.bluetooth.GetBluetoothManagerUseCase
import com.mobile.sample.domain.useCases.game.GetDashboardUseCase
import com.mobile.sample.domain.useCases.game.GetGameInProgressUseCase
import com.mobile.sample.domain.useCases.game.GetTensionUseCase
import com.mobile.sample.other.dispatchAction
import com.mobile.sample.ui.main.dashboard.achievementsScreen.actions.ToGameAction
import com.mobile.sample.ui.main.dashboard.actions.*

class DashboardViewModel(
    gameId: Long,
    getAchievementsUseCase: GetAchievementCategoriesUseCase,
    getGameInProgressUseCase: GetGameInProgressUseCase,
    getTensionUseCase: GetTensionUseCase,
    private val getDashboardUseCase: GetDashboardUseCase,
    private val getBluetoothManagerUseCase: GetBluetoothManagerUseCase,
) : BaseViewModel<DashboardViewState>() {

    private var currTension: TypeOfTension = TypeOfTension.UNDEFINEED


    override fun getInitialState(): DashboardViewState {
        return DashboardViewState()
    }

    fun tensionClicked() {
        ToTensionSettingsAction().dispatchAction(this)
    }

    fun toPlayScreen() {
        ToPlayScreenAction().dispatchAction(this)
    }

    init {

        getAchievementsUseCase.start()
            .subscribe({
                ShowListDataAction(it).dispatchAction(this)
            }, {
                ShowErrorAction(it).dispatchAction(this)
            })
            .also { registerDisposables(it) }

        getTensionUseCase.start()
            .subscribe {
                currTension = TypeOfTension.getTypeByValue(it)
                SetTensionNameAction(currTension).dispatchAction(this)
            }.also { registerDisposables(it) }


        getDashboard()

        getGameInProgressUseCase.start()
            .async()
            .subscribe(
                { it ->
                    SetGameInProgressAction(it.filter { it.gameStatus == TypeOfStatusGame.IN_PROGRESS }).dispatchAction(
                        this
                    )
                },
                {
                    ShowErrorAction(it).dispatchAction(this)
                }
            ).also { registerDisposables(it) }

        getBluetoothManagerUseCase.start().lastUnreadBallConnectionStatus
            .subscribe {
                ShowConnectionStatusAction(it).dispatchAction(this)
            }
            .also { registerDisposables(it) }


        if(gameId>0){
            ToGameAction(gameId).dispatchAction(this)
        }
    }

    private fun getDashboard() {
        getDashboardUseCase.start()
            .async()
            .subscribe(
                { it ->
                    ShowInvitationsAction(
                        it.games?.filter { it.gameModeName == TypeOfGameMode.PRIVATE.backName }!!
                            .filter {
                                it.gameStatusName == TypeOfStatusGame.INVITER.backName || it.gameStatusName == TypeOfStatusGame.INVITED.backName
                            }).dispatchAction(
                        this
                    )
                    ShowAchievementsCountAction(it.achievementCount).dispatchAction(this)
                }, {
                    ShowErrorAction(it).dispatchAction(this)
                })
            .also { registerDisposables(it) }
    }

    fun categoryClicked(id: Long) {
        ToAchievementsListScreenAction(id).dispatchAction(this)
    }

    fun toInvitationsScreen() {
        ToInvitationsScreenAction().dispatchAction(this)
    }

    fun toSetInvitationScreen(model: GameInInvitationData) {
        ToSetInvitationScreenAction(model).dispatchAction(this)
    }

    fun toGameScreen(gameId: Long) {
        ToGameScreenAction(gameId).dispatchAction(this)
    }

    fun closedConnectionWarningStatus(status: Boolean) {
        getBluetoothManagerUseCase.start().lastReadBallConnectionStatus = status
    }

}