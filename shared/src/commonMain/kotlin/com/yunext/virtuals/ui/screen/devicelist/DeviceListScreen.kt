package com.yunext.virtuals.ui.screen.devicelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.yunext.virtuals.ui.common.dialog.CHLoadingDialog
import com.yunext.virtuals.ui.data.processing
import com.yunext.virtuals.ui.screen.adddevice.AddDeviceScreen
import com.yunext.virtuals.ui.screen.devicedetail.DeviceDetailScreen

class DeviceListScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<DeviceListScreenModel> {
            DeviceListScreenModel()
        }
        val state by screenModel.state.collectAsState()

        LaunchedEffect(Unit) {
            screenModel.doGetAllDevice()
        }

        Box(Modifier.fillMaxSize()) {
            DeviceListScreenImpl(
                modifier = Modifier.fillMaxSize(),
                list = state.list,
                onRefresh = {
                    screenModel.doGetAllDevice()
                },
                onDeviceSelected = {
                    navigator.push(DeviceDetailScreen(it))
                }, onActionAdd = {
                    navigator.push(AddDeviceScreen())
                }, onDeviceDelete = screenModel::doDeleteDevice
            )
        }

        if (state.effect.processing) {
            CHLoadingDialog("Loading", dimAmount = .5f) {

            }
        }
    }
}