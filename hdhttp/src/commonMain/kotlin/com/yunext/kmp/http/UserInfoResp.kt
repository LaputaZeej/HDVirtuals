package com.yunext.kmp.http

import com.yunext.kmp.http.core.HttpResponse

data class UserInfoResp(
    val fileId: String? = null,
    val fileName: String? = null,
    val sex: String? = null,
    val userNickName: String? = null,
    val province: String? = null,
    val city: String? = null,
    val region: String? = null,
    val address: String? = null,
    val phone: String? = null
): HttpResponse


