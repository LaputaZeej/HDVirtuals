package com.yunext.virtuals.ui.screen.devicedetail.deviceevent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yunext.kmp.resource.color.app_appColor
import com.yunext.kmp.resource.color.app_blue_light
import com.yunext.kmp.resource.color.app_red
import com.yunext.kmp.resource.color.app_red_light
import com.yunext.kmp.resource.color.app_textColor_333333
import com.yunext.kmp.resource.color.app_textColor_999999
import com.yunext.kmp.ui.compose.CHItemShadowShape
import com.yunext.virtuals.ui.data.EventData
import com.yunext.virtuals.ui.screen.devicedetail.deviceproperty.BottomPart
import com.yunext.virtuals.ui.screen.devicedetail.InputPart
import com.yunext.virtuals.ui.screen.devicedetail.deviceproperty.LabelPart
import com.yunext.virtuals.ui.theme.ItemDefaults
import org.jetbrains.compose.resources.ExperimentalResourceApi


// ----------------- events ----------------- //

/**
 * 事件列表
 */
@Composable
fun ListTslEvent(list: List<EventData>, onClick: (EventData) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(items = list, key = { _, it ->
            it.toString()
        }) { index, it ->
            EventItem(it) {
                onClick(list[index])
            }
        }
    }
}

@Composable
private fun EventItem(data: EventData, onClick: () -> Unit) {
    CHItemShadowShape() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(ItemDefaults.itemShape)
                .background(ItemDefaults.itemBackground)
                .padding(16.dp)
        ) {
            // 头部基本信息
            EventHeaderPart(data = data) {
                onClick()
            }

            Spacer(modifier = Modifier.height(16.dp))
            // 输出
            if (data.output.isNotEmpty()) {
                InputPart("输出", data.output)
                Spacer(modifier = Modifier.height(16.dp))
            }
            // desc
            BottomPart(data.desc)
        }
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
private fun EventHeaderPart(data: EventData, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
//            .hdBorder(true)
    ) {
        Column(
            modifier = Modifier.weight(1f, true), verticalArrangement = Arrangement.Center,

            ) {
            // 名称
            Row(
                modifier = Modifier,
                //.weight(1f, true)
//                    .hdBackground()
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = data.name.run { this.ifEmpty { "未知" } },
                    fontSize = 16.sp,
                    color = app_textColor_333333,
                    fontWeight = FontWeight.Bold
                )
                Text(text = data.key.run {
                    if (this.isEmpty()) "-" else "(${this})"
                }, fontSize = 16.sp, color = app_textColor_999999, fontWeight = FontWeight.Bold)

                //
//                HDImage(
//                    resource = {
//                        HDRes.drawable.icon_twins_log
//                    }, null,
//                    modifier = Modifier.clickablePure {
//                        // 查看详情
//                    }
//                    //.size(24.dp)
//                )
            }


            Spacer(modifier = Modifier.height(8.dp))
            // 状态
            Row(modifier = Modifier) {
                val required = if (data.required) "必须" else "非必须"
                val requiredColor =
                    if (data.required) app_red to app_red_light else app_appColor to app_blue_light
                LabelPart(
                    required,
                    requiredColor.first,
                    requiredColor.second
                )
                Spacer(modifier = Modifier.width(8.dp))

                val text = data.eventType.text
                val color = data.eventType.color
                LabelPart(text, color.first, color.second)
                Spacer(modifier = Modifier.width(8.dp))

            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        // 修改
        Text(
            modifier = Modifier
                .clip(ItemDefaults.editShape)
                .border(width = 1.dp, color = app_appColor, shape = ItemDefaults.editShape)
                .clickable {
                    onClick()
                }
                .padding(horizontal = 8.dp, vertical = 6.dp),
            text = "触发",
            fontSize = 13.sp,
            color = app_appColor
        )
    }
}







