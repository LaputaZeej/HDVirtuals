package com.yunext.virtuals.ui.screen.devicedetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.yunext.virtuals.ui.common.TwinsEmptyView
import com.yunext.virtuals.ui.data.PropertyData

class DeviceDetailSubEventScreen : Screen {

    @Composable
    override fun Content() {

        DeviceDetailSubEventScreenImpl(emptyList())
    }
}

@Composable
internal fun DeviceDetailSubEventScreenImpl(list: List<PropertyData>) {
    Box(Modifier.fillMaxSize()) {
        // HDDebugText("设备详情-事件")
        if (list.isEmpty()) {
            TwinsEmptyView()
        } else {
            Box(Modifier.padding(horizontal = 16.dp)) {
                ListTslEvent(list = list) {
                    //
                }
            }
        }
    }
}