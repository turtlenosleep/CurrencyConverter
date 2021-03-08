package tw.com.robert.yu.paypay.currencyconverter.dao

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorData(
    @field:Json(name = "code") val code: Int,
    @field:Json(name = "info") val info: String
)