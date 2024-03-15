package com.yunext.virtuals.data.device

import com.yunext.kmp.mqtt.data.MQTTParam
import com.yunext.kmp.mqtt.protocol.ProtocolMQTTRule
import com.yunext.kmp.mqtt.protocol.ProtocolMQTTTopic
import com.yunext.virtuals.data.ProjectInfo
import com.yunext.virtuals.data.isYinDu
import com.yunext.virtuals.module.devicemanager.DefaultMqttConvertor
import com.yunext.virtuals.module.devicemanager.DeviceInitializer
import com.yunext.virtuals.module.devicemanager.MQTTConvertor

sealed interface MQTTDevice {

    val rule: ProtocolMQTTRule

    val deviceType: String

    fun createMqttParam(projectInfo: ProjectInfo): MQTTParam

    fun generateId(): String

    fun supportTopics(): Array<ProtocolMQTTTopic> =
        arrayOf(ProtocolMQTTTopic.DOWN, ProtocolMQTTTopic.QUERY)

    fun providerDeviceInitializer(): DeviceInitializer

}

fun MQTTDevice.generateTopic(projectInfo: ProjectInfo, mqttTopic: ProtocolMQTTTopic): String {
    val brand = projectInfo.brand
    val deviceID = generateId()
    if (projectInfo.isYinDu()) {
        return "/$brand/mqtt/$deviceID/${mqttTopic.category}"
    }
    return "/$brand/$deviceType/$deviceID/${mqttTopic.category}"
}

fun MQTTDevice.providerMqttConvertor(): MQTTConvertor {
    return when (this) {
        is AndroidDevice -> DefaultMqttConvertor()
        is TwinsDevice -> DefaultMqttConvertor()
        else->throw IllegalStateException("non convertor")
    }
}






