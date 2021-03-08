package tw.com.robert.yu.paypay.currencyconverter.dao.exchage

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import tw.com.robert.yu.paypay.currencyconverter.dao.ErrorData


@JsonClass(generateAdapter = true)
data class ExchangeRateListData(
    @field:Json(name = "privacy") val privacy: String?,
    @field:Json(name = "quotes") var quotes: MutableMap<String, Double>?,
    @field:Json(name = "source") val source: String?,
    @field:Json(name = "success") val success: Boolean,
    @field:Json(name = "terms") val terms: String?,
    @field:Json(name = "timestamp") val timestamp: Long?,
    @field:Json(name = "error") val error: ErrorData?
)
