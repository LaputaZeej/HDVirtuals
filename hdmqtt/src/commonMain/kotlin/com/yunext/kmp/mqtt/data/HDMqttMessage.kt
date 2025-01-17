package com.yunext.kmp.mqtt.data

import kotlinx.serialization.Serializable

@Serializable
class HDMqttMessage(
    val payload: ByteArray,
    val retained: Boolean,
    val qos: Int,
    val messageId: Int,
    val dup: Boolean,
    val mutable: Boolean = true,
)